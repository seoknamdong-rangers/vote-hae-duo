package com.votehaeduo.service;

import com.votehaeduo.dto.request.VoteSaveRequestDto;
import com.votehaeduo.dto.request.VoteItemRequestDto;
import com.votehaeduo.dto.response.VoteResponseDto;
import com.votehaeduo.entity.Vote;
import com.votehaeduo.repository.VoteRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @InjectMocks
    private VoteService voteService;

    @Test
    void save() {
    }

    @Test
    @DisplayName("투표 전체 조회")
    void findAll() {
        //given
        List<VoteItemRequestDto> voteItems = List.of(new VoteItemRequestDto(1L, "11 ~ 1시 실외"),
                new VoteItemRequestDto(2L, "12 ~ 2 실내"));
        List<Vote> testVotes = List.of(new VoteSaveRequestDto(1L, "12월 15일 풋살 투표", voteItems).toEntity()
                , new VoteSaveRequestDto(2L, "12월 16일 풋살 투표", voteItems).toEntity());
        given(voteRepository.findAll()).willReturn(testVotes);

        //when
        List<VoteResponseDto> voteResponseDto = voteService.findAll();

        //then
        Assertions.assertThat(voteResponseDto.get(0).getName()).isEqualTo("12월 15일 풋살 투표");
    }

    @Test
    void findById() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}