package com.votehaeduo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
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
    @DisplayName("투표 생성")
    void insertVote() throws Exception {
        // given
        VoteResponseDto expectedVoteResponseDto = new VoteResponseDto(
                1L, "1월 8일 풋살", List.of(
                new VoteItemResponseDto(1L, "11시 ~ 1시 실외"),
                new VoteItemResponseDto(2L, "12시 ~ 2시 실내")));
        given(voteService.save(any())).willReturn(expectedVoteResponseDto);

        //when
        String voteRequestDtoJsonString = objectMapper.writeValueAsString(expectedVoteResponseDto);

        //then
        mvc.perform(post("/api/votes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(voteRequestDtoJsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("1월 8일 풋살"))
                .andExpect(jsonPath("$.voteItems.[0].id").value(1))
                .andExpect(jsonPath("$.voteItems.[0].name").value("11시 ~ 1시 실외"))
                .andExpect(jsonPath("$.voteItems.[1].id").value(2))
                .andExpect(jsonPath("$.voteItems.[1].name").value("12시 ~ 2시 실내"));
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
        mvc.perform(get("/api/votes"))
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