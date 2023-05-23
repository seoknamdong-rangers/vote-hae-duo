package com.votehaeduo.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.votehaeduo.dto.request.CreateCommentRequestDto;
import com.votehaeduo.dto.request.DeleteCommentRequestDto;
import com.votehaeduo.dto.request.VoteCreateRequestDto;
import com.votehaeduo.dto.response.*;
import com.votehaeduo.dto.request.*;
import com.votehaeduo.dto.response.*;
import com.votehaeduo.service.VoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VoteController.class)
class VoteControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VoteService voteService;

    @BeforeEach
    void setUp() {
        this.mvc = MockMvcBuilders.standaloneSetup(new VoteController(voteService)).build();
    }

    @Test
    @DisplayName("투표 등록 성공")
    void insertVote_success() throws Exception {
        // given
        VoteCreateResponseDto expectedVoteResponseDto = new VoteCreateResponseDto(
                new VotePayloadResponseDto(1L, "1월 8일 풋살",
                        LocalDate.of(2023, 1, 20),
                        LocalDate.of(2023, 1, 30), "성준",
                        List.of(new VoteItemPayloadResponseDto(1L, "11시 ~ 1시 실외"),
                                new VoteItemPayloadResponseDto(2L, "12시 ~ 2시 실내"))));
        given(voteService.create(any())).willReturn(expectedVoteResponseDto);

        VoteCreateRequestDto voteCreateRequestDto = new VoteCreateRequestDto("1월 8일 풋살",
                LocalDate.of(2023, 1, 20),
                LocalDate.of(2023, 1, 30), "성준",
                List.of(new VoteItemCreateRequestDto("11시 ~ 1시 실외"),
                        new VoteItemCreateRequestDto("12시 ~ 2시 실내")));
        String voteRequestDtoJsonString = objectMapper.writeValueAsString(voteCreateRequestDto);

        // when & then
        mvc.perform(post("/api/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(voteRequestDtoJsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.votePayloadResponseDto.title").value("1월 8일 풋살"))
                .andExpect(jsonPath("$.votePayloadResponseDto.startDate").value("2023-01-20"))
                .andExpect(jsonPath("$.votePayloadResponseDto.endDate").value("2023-01-30"))
                .andExpect(jsonPath("$.votePayloadResponseDto.createdBy").value("성준"))
                .andExpect(jsonPath("$.votePayloadResponseDto.voteItems.[0].title").value("11시 ~ 1시 실외"))
                .andExpect(jsonPath("$.votePayloadResponseDto.voteItems.[1].title").value("12시 ~ 2시 실내"));
    }

    @Test
    @DisplayName("투표 등록 실패")
    void insertVote_fail() throws Exception {
        // given
        VoteCreateRequestDto voteCreateRequestDto = new VoteCreateRequestDto("1월 8일 풋살",
                LocalDate.of(2023, 2, 10),
                LocalDate.of(2023, 2, 20), "성준", List.of());

        String json = objectMapper.writeValueAsString(voteCreateRequestDto);

        // when & then
        mvc.perform(post("/api/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }


    @Test
    @DisplayName("투표 전체 조회")
    void findAllVote() throws Exception {
        // given
        List<FindVoteResponseDto> expectedVotes = List.of(
                new FindVoteResponseDto(1L, "12월 15일 풋살 투표",
                        LocalDate.of(2023, 1, 20),
                        LocalDate.of(2023, 1, 30), "성준",
                        List.of(new FindVoteItemResponseDto(1L, "12 ~ 2 실내", Set.of(1L, 2L)),
                                new FindVoteItemResponseDto(2L, "11 ~ 1 야외", Set.of(1L, 2L))),
                        10L),
                new FindVoteResponseDto(2L, "12월 16일 풋살 투표",
                        LocalDate.of(2023, 1, 20),
                        LocalDate.of(2023, 1, 30), "성준",
                        List.of(new FindVoteItemResponseDto(1L, "12 ~ 2 실내", Set.of(1L, 2L)),
                                new FindVoteItemResponseDto(2L, "11 ~ 1 야외", Set.of(1L, 2L))),
                        10L)
        );
        given(voteService.findAll()).willReturn(expectedVotes);

        // when & then
        mvc.perform(get("/api/votes"))
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].title").value("12월 15일 풋살 투표"))
                .andExpect(jsonPath("$.[0].startDate").value("2023-01-20"))
                .andExpect(jsonPath("$.[0].endDate").value("2023-01-30"))
                .andExpect(jsonPath("$.[0].createdBy").value("성준"))
                .andExpect(jsonPath("$.[0].voteItems.[0].memberIds.[*]").value(containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$.[0].voteItems.[0].id").value(1))
                .andExpect(jsonPath("$.[0].voteItems.[0].title").value("12 ~ 2 실내"))
                .andExpect(jsonPath("$.[0].voteItems.[1].id").value(2))
                .andExpect(jsonPath("$.[0].voteItems.[1].title").value("11 ~ 1 야외"))
                .andExpect(jsonPath("$.[0].uniqueCount").value(10))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[1].title").value("12월 16일 풋살 투표"))
                .andExpect(jsonPath("$.[1].startDate").value("2023-01-20"))
                .andExpect(jsonPath("$.[1].endDate").value("2023-01-30"))
                .andExpect(jsonPath("$.[1].createdBy").value("성준"))
                .andExpect(jsonPath("$.[1].voteItems.[0].memberIds.[*]").value(containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$.[1].voteItems.[0].id").value(1))
                .andExpect(jsonPath("$.[1].voteItems.[0].title").value("12 ~ 2 실내"))
                .andExpect(jsonPath("$.[1].voteItems.[1].id").value(2))
                .andExpect(jsonPath("$.[1].voteItems.[1].title").value("11 ~ 1 야외"))
                .andExpect(jsonPath("$.[1].uniqueCount").value(10));
    }

    @Test
    @DisplayName("투표 상세 조회")
    void findByIdVote() throws Exception {
        //given
        VoteResponseDto expectedVoteResponseDto = new VoteResponseDto(1L, "1월 10일 풋살",
                LocalDate.of(2023, 1, 20),
                LocalDate.of(2023, 1, 30), "성준",
                List.of(new VoteItemResponseDto(1L, "11시 ~ 1시 실외", Set.of(1L, 2L), 2L),
                        new VoteItemResponseDto(2L, "12시 ~ 2시 실내", Set.of(3L, 4L), 2L)),
                4L);
        given(voteService.findById(any())).willReturn(expectedVoteResponseDto);

        //when & then
        mvc.perform(get("/api/votes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("1월 10일 풋살"))
                .andExpect(jsonPath("$.voteItems.[0].id").value(1))
                .andExpect(jsonPath("$.voteItems.[0].title").value("11시 ~ 1시 실외"))
                .andExpect(jsonPath("$.voteItems.[1].id").value(2))
                .andExpect(jsonPath("$.voteItems.[1].title").value("12시 ~ 2시 실내"));
    }

    @Test
    @DisplayName("투표 수정")
    void updateVote() throws Exception {
        // given
        VoteResponseDto voteResponseDto = new VoteResponseDto(1L, "1월 9일 풋살",
                LocalDate.of(2023, 1, 20),
                LocalDate.of(2023, 1, 25),
                "성준",
                List.of(new VoteItemResponseDto(1L, "11시 ~ 1시 실외", Set.of(1L, 2L), 2L),
                        new VoteItemResponseDto(2L, "12시 ~ 2시 실내", Set.of(3L, 4L), 2L)),
                4L);
        given(voteService.update(any(), any())).willReturn(voteResponseDto);

        VoteUpdateRequestDto voteUpdateRequestDto = new VoteUpdateRequestDto("1월 9일 풋살",
                LocalDate.of(2023, 1, 20),
                LocalDate.of(2023, 1, 25),
                "성준",
                List.of(new VoteItemUpdateRequestDto(1L, "11시 ~ 1시 실외", Set.of(1L, 2L)),
                        new VoteItemUpdateRequestDto(2L, "12시 ~ 2시 실내", Set.of(3L, 4L))));
        String voteRequestDtoJsonString = objectMapper.writeValueAsString(voteUpdateRequestDto);

        // when & then
        mvc.perform(put("/api/votes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(voteRequestDtoJsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("1월 9일 풋살"))
                .andExpect(jsonPath("$.startDate").value("2023-01-20"))
                .andExpect(jsonPath("$.endDate").value("2023-01-25"))
                .andExpect(jsonPath("$.createdBy").value("성준"))
                .andExpect(jsonPath("$.voteItems.[0].memberIds.[*]").value(containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$.voteItems.[0].id").value(1))
                .andExpect(jsonPath("$.voteItems.[0].title").value("11시 ~ 1시 실외"))
                .andExpect(jsonPath("$.voteItems.[1].memberIds.[*]").value(containsInAnyOrder(3, 4)))
                .andExpect(jsonPath("$.voteItems.[1].id").value(2))
                .andExpect(jsonPath("$.voteItems.[1].title").value("12시 ~ 2시 실내"));
    }

    @Test
    @DisplayName("투표 삭제")
    void deleteVote() throws Exception {
        // given
        given(voteService.delete(any())).willReturn(true);

        // when & then
        mvc.perform(delete("/api/votes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));
    }

    @Test
    @DisplayName("투표 하기")
    void voting() throws Exception {
        // given
        VotingResponseDto expectedVotingResponseDto = new VotingResponseDto(
                new VotePayloadResponseDto(1L, "1월 8일 풋살",
                        LocalDate.of(2023, 1, 20),
                        LocalDate.of(2023, 1, 30), "성준",
                        List.of(new VoteItemPayloadResponseDto(1L, "11시 ~ 1시 실외"),
                                new VoteItemPayloadResponseDto(2L, "12시 ~ 2시 실내"))),
                new VoteMemberResponseDto(Set.of(1L, 2L), 2L),
                List.of(new VoteMemberResponseDto(Set.of(1L, 2L), 2L),
                        new VoteMemberResponseDto(Set.of(1L), 1L)));
        given(voteService.voting(any(), any())).willReturn(expectedVotingResponseDto);

        VotingRequestDto votingRequestDto = new VotingRequestDto(1L, List.of(1L, 2L));
        String votingRequestDtoJsonString = objectMapper.writeValueAsString(votingRequestDto);

        // when & then
        mvc.perform(post("/api/votes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(votingRequestDtoJsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.votePayloadResponseDto.id").value(1))
                .andExpect(jsonPath("$.votePayloadResponseDto.title").value("1월 8일 풋살"))
                .andExpect(jsonPath("$.votePayloadResponseDto.startDate").value("2023-01-20"))
                .andExpect(jsonPath("$.votePayloadResponseDto.endDate").value("2023-01-30"))
                .andExpect(jsonPath("$.votePayloadResponseDto.createdBy").value("성준"))
                .andExpect(jsonPath("$.votePayloadResponseDto.voteItems.[0].title").value("11시 ~ 1시 실외"))
                .andExpect(jsonPath("$.votePayloadResponseDto.voteItems.[1].title").value("12시 ~ 2시 실내"))
                .andExpect(jsonPath("$.uniqueCount.memberIds.[*]").value(containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$.uniqueCount.uniqueCount").value(2))
                .andExpect(jsonPath("$.uniqueCountByVoteItem.[0].memberIds[*]").value(containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$.uniqueCountByVoteItem.[0].uniqueCount").value(2L))
                .andExpect(jsonPath("$.uniqueCountByVoteItem.[1].memberIds[*]").value(containsInAnyOrder(1)))
                .andExpect(jsonPath("$.uniqueCountByVoteItem.[1].uniqueCount").value(1L));
    }

    @Test
    @DisplayName("댓글 등록")
    void insertComment() throws Exception {
        // given
        CreateCommentResponseDto expectedCreateCommentResponseDto = new CreateCommentResponseDto(1L, "재밌겠다",
                LocalDate.of(2023, 5, 19), "성준", 1L);
        given(voteService.createComment(any(), any())).willReturn(expectedCreateCommentResponseDto);

        CreateCommentRequestDto expectedCreateCommentRequestDto = new CreateCommentRequestDto("성준",
                LocalDate.of(2023, 5, 19), "성준", 1L);
        String createCommentRequestDto = objectMapper.writeValueAsString(expectedCreateCommentRequestDto);

        // when & then
        mvc.perform(post("/api/votes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createCommentRequestDto))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.content").value("재밌겠다"))
                .andExpect(jsonPath("$.date").value("2023-05-19"))
                .andExpect(jsonPath("$.createdBy").value("성준"))
                .andExpect(jsonPath("$.memberId").value(1));
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteComment() throws Exception {
        // given
        given(voteService.deleteComment(any(),any())).willReturn(true);

        DeleteCommentRequestDto expectedDeleteCommentRequestDto = new DeleteCommentRequestDto(1L,1L);
        String deleteCommentRequestDto = objectMapper.writeValueAsString(expectedDeleteCommentRequestDto);

        // when & then
        mvc.perform(delete("/api/votes/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(deleteCommentRequestDto))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));
    }

}