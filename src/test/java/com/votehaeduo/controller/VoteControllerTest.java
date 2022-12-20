package com.votehaeduo.controller;

import com.votehaeduo.dto.request.VoteSaveRequestDto;
import com.votehaeduo.dto.request.VoteItemRequestDto;
import com.votehaeduo.dto.response.VoteResponseDto;
import com.votehaeduo.entity.Vote;
import com.votehaeduo.service.VoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class VoteControllerTest {

    private MockMvc mvc;

    @MockBean
    private VoteService voteService;

    @Test
    @DisplayName("투표 생성")
    void insertVote() throws Exception {

    }

    @BeforeEach
    void setUp() {
        this.mvc = MockMvcBuilders.standaloneSetup(new VoteController(voteService)).build();
    }

    @Test
    @DisplayName("투표 전체 조회")
    void findAllVote() throws Exception {
        List<VoteItemRequestDto> voteItems = List.of(new VoteItemRequestDto(1L, "11 ~ 1시 실외"),
                new VoteItemRequestDto(2L, "12 ~ 2 실내"));
        List<Vote> testVotes = List.of(new VoteSaveRequestDto(1L, "12월 15일 풋살 투표", voteItems).toEntity()
                , new VoteSaveRequestDto(2L, "12월 16일 풋살 투표", voteItems).toEntity()
                , new VoteSaveRequestDto(3L, "12월 17일 풋살 투표", voteItems).toEntity()
                , new VoteSaveRequestDto(4L, "12월 18일 풋살 투표", voteItems).toEntity()
        );
        List<VoteResponseDto> votes = testVotes.stream()
                .map(VoteResponseDto::from)
                .collect(Collectors.toList());
        given(voteService.findAll()).willReturn(votes);

        mvc.perform(get("/votes"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].name").value("12월 15일 풋살 투표"))
                .andExpect(jsonPath("$.[1].name").value("12월 16일 풋살 투표"));
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