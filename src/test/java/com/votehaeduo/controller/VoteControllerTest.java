package com.votehaeduo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.votehaeduo.dto.request.VoteCreateRequestDto;
import com.votehaeduo.dto.response.VoteItemCreateResponseDto;
import com.votehaeduo.dto.response.VoteCreateResponseDto;
import com.votehaeduo.dto.response.FindVoteItemResponseDto;
import com.votehaeduo.dto.response.FindVoteResponseDto;
import com.votehaeduo.dto.response.VoteItemResponseDto;
import com.votehaeduo.dto.response.VoteResponseDto;
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
        VoteCreateResponseDto expectedVoteResponseDto = new VoteCreateResponseDto(1L, "1월 8일 풋살",
                LocalDate.of(2023, 1, 20),
                LocalDate.of(2023, 1, 30), "성준",
                List.of(new VoteItemCreateResponseDto(1L, "11시 ~ 1시 실외"),
                        new VoteItemCreateResponseDto(2L, "12시 ~ 2시 실내")));
        given(voteService.create(any())).willReturn(expectedVoteResponseDto);
        String voteRequestDtoJsonString = objectMapper.writeValueAsString(expectedVoteResponseDto);

        // when & then
        mvc.perform(post("/api/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(voteRequestDtoJsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("1월 8일 풋살"))
                .andExpect(jsonPath("$.startDate").value("2023-01-20"))
                .andExpect(jsonPath("$.endDate").value("2023-01-30"))
                .andExpect(jsonPath("$.createdBy").value("성준"))
                .andExpect(jsonPath("$.voteItems.[0].id").value(1))
                .andExpect(jsonPath("$.voteItems.[0].title").value("11시 ~ 1시 실외"))
                .andExpect(jsonPath("$.voteItems.[1].id").value(2))
                .andExpect(jsonPath("$.voteItems.[1].title").value("12시 ~ 2시 실내"));
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
        List<FindVoteResponseDto> votes = List.of(
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
        given(voteService.findAll()).willReturn(votes);
        String voteRequestDtoJsonString = objectMapper.writeValueAsString(votes);

        //when & then
        mvc.perform(get("/api/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(voteRequestDtoJsonString))
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
    void findByIdVote() {
    }

    @Test
    @DisplayName("투표 수정")
    void updateVote() throws Exception {
        // given
        Set<Long> memberIds = Set.of(1L, 2L);
        VoteResponseDto voteResponseDto = new VoteResponseDto(1L, "1월 9일 풋살",
                LocalDate.of(2023, 1, 20),
                LocalDate.of(2023, 1, 25),
                "성준",
                List.of(new VoteItemResponseDto(1L, "11시 ~ 1시 실외", Set.of(1L, 2L)),
                        new VoteItemResponseDto(2L, "12시 ~ 2시 실내", Set.of(3L, 4L))));
        given(voteService.update(any(), any())).willReturn(voteResponseDto);
        String voteRequestDtoJsonString = objectMapper.writeValueAsString(voteResponseDto);

        // when, then
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
        //given
        given(voteService.delete(any())).willReturn(true);

        //when & then
        mvc.perform(delete("/api/votes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));
    }

}