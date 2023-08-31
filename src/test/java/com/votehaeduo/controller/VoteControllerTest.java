package com.votehaeduo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.votehaeduo.dto.*;
import com.votehaeduo.dto.request.CreateCommentRequest;
import com.votehaeduo.dto.request.DeleteCommentRequest;
import com.votehaeduo.dto.request.CreateVoteRequest;
import com.votehaeduo.dto.response.*;
import com.votehaeduo.dto.request.*;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
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

    private final LocalDate testStartDate = LocalDate.now();
    private final LocalDate testEndDate = LocalDate.now().plusDays(3);

    @BeforeEach
    void setUp() {
        this.mvc = MockMvcBuilders.standaloneSetup(new VoteController(voteService)).build();
    }

    @Test
    @DisplayName("투표 등록")
    void insertVote_success() throws Exception {
        // given
        CreateVoteResponse expectedVoteResponseDto = new CreateVoteResponse(
                new VotePayload(1L,
                        "금주 풋살 투표",
                        testStartDate,
                        testEndDate,
                        1L,
                        List.of(new VoteItemPayload(1L, "11시 ~ 1시 실외", Set.of(2L, 3L)),
                                new VoteItemPayload(2L, "12시 ~ 2시 실내", Set.of(4L, 5L))),
                        List.of(new CommentPayload(),
                                new CommentPayload())),
                "킴대세");
        given(voteService.create(any())).willReturn(expectedVoteResponseDto);

        CreateVoteRequest createVoteRequest = new CreateVoteRequest("금주 풋살 투표",
                testStartDate,
                testEndDate, 1L,
                List.of(new CreateVoteItem("11시 ~ 1시 실외"),
                        new CreateVoteItem("12시 ~ 2시 실내")));
        String voteRequestDtoJsonString = objectMapper.writeValueAsString(createVoteRequest);

        // when & then
        mvc.perform(post("/api/votes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(voteRequestDtoJsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.votePayload.id").value(1L))
                .andExpect(jsonPath("$.votePayload.title").value("금주 풋살 투표"))
                .andExpect(jsonPath("$.votePayload.startDate").value(String.valueOf(testStartDate)))
                .andExpect(jsonPath("$.votePayload.endDate").value(String.valueOf(testEndDate)))
                .andExpect(jsonPath("$.votePayload.createdMemberId").value(1L))
                .andExpect(jsonPath("$.votePayload.voteItems.[0].id").value(1L))
                .andExpect(jsonPath("$.votePayload.voteItems.[0].title").value("11시 ~ 1시 실외"))
                .andExpect(jsonPath("$.votePayload.voteItems.[0].memberIds.[*]").value(containsInAnyOrder(2, 3)))
                .andExpect(jsonPath("$.votePayload.voteItems.[1].id").value(2L))
                .andExpect(jsonPath("$.votePayload.voteItems.[1].title").value("12시 ~ 2시 실내"))
                .andExpect(jsonPath("$.votePayload.voteItems.[1].memberIds.[*]").value(containsInAnyOrder(4, 5)))
                .andExpect(jsonPath("$.nickname").value("킴대세"));
    }

    @Test
    @DisplayName("투표 전체 조회")
    void findAllVote() throws Exception {
        // given
        List<FindVoteResponse> expectedVotes = List.of(
                new FindVoteResponse(1L, "12월 15일 풋살 투표",
                        LocalDate.of(2023, 1, 20),
                        LocalDate.of(2023, 1, 30), 1L,
                        List.of(new VoteItemDetails(1L, "12 ~ 2 실내", Set.of(1L, 2L), 2L),
                                new VoteItemDetails(2L, "11 ~ 1 야외", Set.of(1L, 2L), 2L)),
                        4L),
                new FindVoteResponse(2L, "12월 16일 풋살 투표",
                        LocalDate.of(2023, 1, 20),
                        LocalDate.of(2023, 1, 30), 2L,
                        List.of(new VoteItemDetails(1L, "12 ~ 2 실내", Set.of(1L, 2L), 2L),
                                new VoteItemDetails(2L, "11 ~ 1 야외", Set.of(1L, 2L), 2L)),
                        4L)
        );
        given(voteService.findAll()).willReturn(expectedVotes);

        // when & then
        mvc.perform(get("/api/votes"))
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].title").value("12월 15일 풋살 투표"))
                .andExpect(jsonPath("$.[0].startDate").value("2023-01-20"))
                .andExpect(jsonPath("$.[0].endDate").value("2023-01-30"))
                .andExpect(jsonPath("$.[0].createdMemberId").value(1L))
                .andExpect(jsonPath("$.[0].voteItems.[0].memberIds.[*]").value(containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$.[0].voteItems.[0].id").value(1))
                .andExpect(jsonPath("$.[0].voteItems.[0].title").value("12 ~ 2 실내"))
                .andExpect(jsonPath("$.[0].voteItems.[1].id").value(2))
                .andExpect(jsonPath("$.[0].voteItems.[1].title").value("11 ~ 1 야외"))
                .andExpect(jsonPath("$.[0].uniqueCount").value(4))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[1].title").value("12월 16일 풋살 투표"))
                .andExpect(jsonPath("$.[1].startDate").value("2023-01-20"))
                .andExpect(jsonPath("$.[1].endDate").value("2023-01-30"))
                .andExpect(jsonPath("$.[1].createdMemberId").value(2L))
                .andExpect(jsonPath("$.[1].voteItems.[0].memberIds.[*]").value(containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$.[1].voteItems.[0].id").value(1))
                .andExpect(jsonPath("$.[1].voteItems.[0].title").value("12 ~ 2 실내"))
                .andExpect(jsonPath("$.[1].voteItems.[1].id").value(2))
                .andExpect(jsonPath("$.[1].voteItems.[1].title").value("11 ~ 1 야외"))
                .andExpect(jsonPath("$.[1].uniqueCount").value(4));
    }

    @Test
    @DisplayName("투표 상세 조회")
    void findByIdVote() throws Exception {
        // given
        FindByIdVoteResponse expectedFindByIdVoteResponse = new FindByIdVoteResponse(1L, "1월 10일 풋살",
                LocalDate.of(2023, 1, 20),
                LocalDate.of(2023, 1, 30), 1L,
                List.of(new VoteItemDetails(1L, "11시 ~ 1시 실외", Set.of(1L, 2L), 2L),
                        new VoteItemDetails(2L, "12시 ~ 2시 실내", Set.of(3L, 4L), 2L)),
                List.of(new CommentPayload(),
                        new CommentPayload()),
                4L);
        given(voteService.findById(any())).willReturn(expectedFindByIdVoteResponse);

        // when & then
        mvc.perform(get("/api/votes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("1월 10일 풋살"))
                .andExpect(jsonPath("$.startDate").value("2023-01-20"))
                .andExpect(jsonPath("$.endDate").value("2023-01-30"))
                .andExpect(jsonPath("$.voteItems.[0].id").value(1))
                .andExpect(jsonPath("$.voteItems.[0].title").value("11시 ~ 1시 실외"))
                .andExpect(jsonPath("$.voteItems.[0].memberIds.[*]").value(containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$.voteItems.[0].voteItemMemberCount").value(2L))
                .andExpect(jsonPath("$.voteItems.[1].id").value(2L))
                .andExpect(jsonPath("$.voteItems.[1].title").value("12시 ~ 2시 실내"))
                .andExpect(jsonPath("$.voteItems.[1].memberIds.[*]").value(containsInAnyOrder(3, 4)))
                .andExpect(jsonPath("$.voteItems.[1].voteItemMemberCount").value(2L));
    }

    @Test
    @DisplayName("투표 수정")
    void updateVote() throws Exception {
        // given
        UpdateVoteResponse updateVoteResponse = new UpdateVoteResponse(1L, "1월 9일 풋살",
                LocalDate.of(2023, 1, 20),
                LocalDate.of(2023, 1, 25),
                1L,
                List.of(new VoteItemDetails(1L, "11시 ~ 1시 실외", Set.of(1L, 2L), 2L),
                        new VoteItemDetails(2L, "12시 ~ 2시 실내", Set.of(3L, 4L), 2L)),
                4L);
        given(voteService.update(any(), any())).willReturn(updateVoteResponse);

        UpdateVoteRequest updateVoteRequest = new UpdateVoteRequest("1월 9일 풋살",
                LocalDate.of(2023, 1, 25),
                List.of(new VoteItemPayload(1L, "11시 ~ 1시 실외", Set.of(1L, 2L)),
                        new VoteItemPayload(2L, "12시 ~ 2시 실내", Set.of(3L, 4L))));
        String voteRequestDtoJsonString = objectMapper.writeValueAsString(updateVoteRequest);

        // when & then
        mvc.perform(put("/api/votes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(voteRequestDtoJsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("1월 9일 풋살"))
                .andExpect(jsonPath("$.startDate").value("2023-01-20"))
                .andExpect(jsonPath("$.endDate").value("2023-01-25"))
                .andExpect(jsonPath("$.createdMemberId").value(1L))
                .andExpect(jsonPath("$.voteItems.[0].id").value(1L))
                .andExpect(jsonPath("$.voteItems.[0].title").value("11시 ~ 1시 실외"))
                .andExpect(jsonPath("$.voteItems.[0].memberIds.[*]").value(containsInAnyOrder(1, 2)))
                .andExpect(jsonPath("$.voteItems.[0].voteItemMemberCount").value(2L))
                .andExpect(jsonPath("$.voteItems.[1].id").value(2L))
                .andExpect(jsonPath("$.voteItems.[1].title").value("12시 ~ 2시 실내"))
                .andExpect(jsonPath("$.voteItems.[1].memberIds.[*]").value(containsInAnyOrder(3, 4)))
                .andExpect(jsonPath("$.voteItems.[1].voteItemMemberCount").value(2L));
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
        VotingResponse expectedVotingResponse = new VotingResponse(
                1L, "1월 8일 풋살",
                LocalDate.of(2023, 6, 8),
                LocalDate.of(2023, 6, 30), 1L,
                List.of(new VoteItemDetails(1L, "11시 ~ 1시 실외", Set.of(2L, 3L), 2L),
                        new VoteItemDetails(2L, "12시 ~ 2시 실내", Set.of(2L, 3L), 2L)),
                List.of(new CommentPayload(1L, "재밌겠다", "킴대세",
                        LocalDate.of(2023, 6, 9), 1L)),
                Set.of(2L, 3L), 2L);

        given(voteService.voting(any(), any())).willReturn(expectedVotingResponse);

        VotingRequest votingRequest = new VotingRequest(1L, List.of(1L, 2L));
        String votingRequestDtoJsonString = objectMapper.writeValueAsString(votingRequest);

        // when & then
        mvc.perform(post("/api/votes/1/vote-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(votingRequestDtoJsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("1월 8일 풋살"))
                .andExpect(jsonPath("$.startDate").value("2023-06-08"))
                .andExpect(jsonPath("$.endDate").value("2023-06-30"))
                .andExpect(jsonPath("$.createdMemberId").value(1L))
                .andExpect(jsonPath("$.voteItems.[0].id").value(1L))
                .andExpect(jsonPath("$.voteItems.[0].title").value("11시 ~ 1시 실외"))
                .andExpect(jsonPath("$.voteItems.[0].memberIds.[*]").value(containsInAnyOrder(2, 3)))
                .andExpect(jsonPath("$.voteItems.[0].voteItemMemberCount").value(2L))
                .andExpect(jsonPath("$.voteItems.[1].id").value(2L))
                .andExpect(jsonPath("$.voteItems.[1].title").value("12시 ~ 2시 실내"))
                .andExpect(jsonPath("$.voteItems.[1].memberIds.[*]").value(containsInAnyOrder(2, 3)))
                .andExpect(jsonPath("$.voteItems.[1].voteItemMemberCount").value(2L))
                .andExpect(jsonPath("$.voteComments.[0].id").value(1L))
                .andExpect(jsonPath("$.voteComments.[0].content").value("재밌겠다"))
                .andExpect(jsonPath("$.voteComments.[0].nickname").value("킴대세"))
                .andExpect(jsonPath("$.voteComments.[0].date").value("2023-06-09"))
                .andExpect(jsonPath("$.voteComments.[0].memberId").value(1L))
                .andExpect(jsonPath("$.voteMemberIds.[*]").value(containsInAnyOrder(2, 3)))
                .andExpect(jsonPath("$.voteMemberCount").value(2L));
    }

    @Test
    @DisplayName("댓글 등록")
    void insertComment() throws Exception {
        // given
        CreateCommentResponse expectedCreateCommentResponse = new CreateCommentResponse(1L, "재밌겠다",
                LocalDate.of(2023, 6, 10), "킴대세", 1L);
        given(voteService.createComment(any(), any())).willReturn(expectedCreateCommentResponse);

        CreateCommentRequest expectedCreateCommentRequest = new CreateCommentRequest("재밌겠다",
                LocalDate.of(2023, 6, 10), 1L);
        String createCommentRequestDto = objectMapper.writeValueAsString(expectedCreateCommentRequest);

        // when & then
        mvc.perform(post("/api/votes/1/vote-comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createCommentRequestDto))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.content").value("재밌겠다"))
                .andExpect(jsonPath("$.date").value("2023-06-10"))
                .andExpect(jsonPath("$.nickname").value("킴대세"))
                .andExpect(jsonPath("$.memberId").value(1L));
    }

    @Test
    @DisplayName("댓글 삭제")
    void deleteComment() throws Exception {
        // given
        given(voteService.deleteComment(any(), any(), any())).willReturn(true);

        DeleteCommentRequest expectedDeleteCommentRequest = new DeleteCommentRequest(1L);
        String deleteCommentRequestDto = objectMapper.writeValueAsString(expectedDeleteCommentRequest);

        // when & then
        mvc.perform(delete("/api/votes/1/comments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(deleteCommentRequestDto))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));
    }

    @Test
    @DisplayName("팀 매칭")
    void createTeam() throws Exception {
        // given
        TeamPayload teamPayload = TeamPayload.builder()
                .id(1L)
                .teamMembers(Set.of("성준, 성욱", "준성, 영수"))
                .createdMemberId(1L)
                .voteId(1L)
                .build();
        CreateTeamResponse createTeamResponse = new CreateTeamResponse(teamPayload);
        given(voteService.createTeam(any(), any())).willReturn(createTeamResponse);

        CreateTeamRequest createTeamRequest = CreateTeamRequest.builder()
                .memberIds(Set.of(1L, 2L, 3L, 4L))
                .teamCount(2L)
                .createdMemberId(1L)
                .build();
        String voteRequestDtoJsonString = objectMapper.writeValueAsString(createTeamRequest);

        // when & then
        mvc.perform(post("/api/votes/1/vote-teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(voteRequestDtoJsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teamPayload.id").value(1L))
                .andExpect(jsonPath("$.teamPayload.teamMembers").value(containsInAnyOrder("성준, 성욱", "준성, 영수")))
                .andExpect(jsonPath("$.teamPayload.createdMemberId").value(1L))
                .andExpect(jsonPath("$.teamPayload.voteId").value(1L));
    }

}