package com.votehaeduo.controller;

import com.votehaeduo.dto.request.VoteSaveRequestDto;
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

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class VoteControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private VoteService voteService;

    @BeforeEach
    void setUp() {
        this.mvc = MockMvcBuilders.standaloneSetup(new VoteController(voteService)).build();
    }

    @Test
    @DisplayName("투표 생성")
    void insertVote() {

        // given
        VoteSaveRequestDto testVoteSaveRequestDto = VoteSaveRequestDto.builder().build();

        //when
        voteService.save(testVoteSaveRequestDto);

        //then
        verify(voteService, times(1)).save(testVoteSaveRequestDto);
        verify(voteService).save(testVoteSaveRequestDto);
    }


    @Test
    @DisplayName("투표 전체 조회")
    void findAllVote() throws Exception {

        // given
        List<VoteResponseDto> votes = List.of(
                new VoteResponseDto(1L, "12월 15일 풋살 투표", List.of(
                        new VoteItemResponseDto(1L, "12 ~ 2 실내"),
                        new VoteItemResponseDto(2L, "11 ~ 1 야외"))),
                new VoteResponseDto(2L, "12월 16일 풋살 투표", List.of(
                        new VoteItemResponseDto(1L, "12 ~ 2 실내"),
                        new VoteItemResponseDto(2L, "11 ~ 1 야외")))
        );
        given(voteService.findAll()).willReturn(votes);

        //when & then
        mvc.perform(get("/votes"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].name").value("12월 15일 풋살 투표"))
                .andExpect(jsonPath("$.[0].voteItems.[0].id").value(1))
                .andExpect(jsonPath("$.[0].voteItems.[0].name").value("12 ~ 2 실내"))
                .andExpect(jsonPath("$.[0].voteItems.[1].id").value(2))
                .andExpect(jsonPath("$.[0].voteItems.[1].name").value("11 ~ 1 야외"))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[1].name").value("12월 16일 풋살 투표"))
                .andExpect(jsonPath("$.[1].voteItems.[0].id").value(1))
                .andExpect(jsonPath("$.[1].voteItems.[0].name").value("12 ~ 2 실내"))
                .andExpect(jsonPath("$.[1].voteItems.[1].id").value(2))
                .andExpect(jsonPath("$.[1].voteItems.[1].name").value("11 ~ 1 야외"));
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