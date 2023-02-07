package com.votehaeduo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.votehaeduo.dto.response.GetVoteItemResponseDto;
import com.votehaeduo.dto.response.GetVoteResponseDto;
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

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    @DisplayName("투표 등록")
    void insertVote() {
    }


    @Test
    @DisplayName("투표 전체 조회")
    void findAllVote() throws Exception {
        // given
        List<GetVoteResponseDto> votes = List.of(
                new GetVoteResponseDto(1L, "12월 15일 풋살 투표",
                        LocalDate.of(2023, 1, 20),
                        LocalDate.of(2023, 1, 30), "성준",
                        List.of(new GetVoteItemResponseDto(1L, "12 ~ 2 실내", Set.of(1L, 2L)),
                                new GetVoteItemResponseDto(2L, "11 ~ 1 야외", Set.of(1L, 2L))),
                        10L),
                new GetVoteResponseDto(2L, "12월 16일 풋살 투표",
                        LocalDate.of(2023, 1, 20),
                        LocalDate.of(2023, 1, 30), "성준",
                        List.of(new GetVoteItemResponseDto(1L, "12 ~ 2 실내", Set.of(1L, 2L)),
                                new GetVoteItemResponseDto(2L, "11 ~ 1 야외", Set.of(1L, 2L))),
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
    void updateVote() {
    }

    @Test
    @DisplayName("투표 삭제")
    void deleteVote() {
    }

}