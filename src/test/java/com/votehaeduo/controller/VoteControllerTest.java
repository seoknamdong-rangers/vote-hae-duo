package com.votehaeduo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    void insertVote() throws Exception {

    }


    @Test
    @DisplayName("투표 전체 조회")
    void findAllVote() throws Exception {

    }

    @Test
    @DisplayName("투표 상세 조회")
    void findByIdVote() {
    }

    @Test
    @DisplayName("투표 수정")
    void updateVote() throws Exception {
        //given
        VoteResponseDto voteResponseDto = new VoteResponseDto(1L, "1월 9일 풋살",
                LocalDate.of(2023, 1, 20),
                LocalDate.of(2023, 1, 25),
                "성준",
                List.of(new VoteItemResponseDto(1L, "11시 ~ 1시 실외", Set.of(1L, 2L)),
                        new VoteItemResponseDto(2L, "12시 ~ 2시 실내", Set.of(1L, 2L))));
        given(voteService.update(any(), any())).willReturn(voteResponseDto);

        //when
        String voteRequestDtoJsonString = objectMapper.writeValueAsString(voteResponseDto);

        //then
        mvc.perform(put("/api/votes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(voteRequestDtoJsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("1월 9일 풋살"))
                .andExpect(jsonPath("$.startDate").value("2023-01-20"))
                .andExpect(jsonPath("$.endDate").value("2023-01-25"))
                .andExpect(jsonPath("$.createdBy").value("성준"))
                .andExpect(jsonPath("$.voteItems.[0].memberIds", containsInAnyOrder(1L,2L)))
                .andExpect(jsonPath("$.voteItems.[0].id").value(1))
                .andExpect(jsonPath("$.voteItems.[0].title").value("11시 ~ 1시 실외"))
                .andExpect(jsonPath("$.voteItems.[1].id").value(2))
                .andExpect(jsonPath("$.voteItems.[1].title").value("12시 ~ 2시 실내"));
    }

    @Test
    @DisplayName("투표 삭제")
    void deleteVote() {
    }

}