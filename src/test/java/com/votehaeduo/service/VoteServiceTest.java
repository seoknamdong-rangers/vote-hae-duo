package com.votehaeduo.service;

import com.votehaeduo.dto.request.VoteItemSaveRequestDto;
import com.votehaeduo.dto.request.VoteSaveRequestDto;
import com.votehaeduo.dto.response.VoteItemResponseDto;
import com.votehaeduo.dto.response.VoteResponseDto;
import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import com.votehaeduo.repository.VoteRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @InjectMocks
    private VoteService voteService;

    @Test
    @DisplayName("투표 등록")
    void save() {
        //given
        Vote vote = Vote.builder()
                .id(1L)
                .name("1월 9일 풋살 투표")
                .build();
        List<VoteItem> voteItems = List.of(VoteItem.builder()
                        .id(1L)
                        .name("11시 ~ 1시 실외")
                        .vote(vote)
                        .build(),
                VoteItem.builder()
                        .id(2L)
                        .name("12시 ~ 2시 실내")
                        .vote(vote)
                        .build());
        vote.addItems(voteItems);
        VoteResponseDto expectedResult = VoteResponseDto.from(vote);
        given(voteRepository.save(any())).willReturn(vote);

        //when
        VoteResponseDto voteResponseDto = voteService.save(VoteSaveRequestDto.builder()
                .title("1월 9일 풋살 투표")
                .voteItems(List.of(
                        VoteItemSaveRequestDto.builder().title("11시 ~ 1시 실외").build(),
                        VoteItemSaveRequestDto.builder().title("12시 ~ 2시 실내").build())
                ).build());

        //then
        Assertions.assertThat(voteResponseDto).usingRecursiveComparison().isEqualTo(expectedResult);

    }

    @Test
    @DisplayName("투표 전체 조회")
    void findAll() {
        //given
        Long id = new Random().nextLong();
        Vote vote = Vote.builder()
                .id(id)
                .name("12월 15일 풋살 투표")
                .build();
        List<VoteItem> voteItems = List.of(VoteItem.builder()
                        .id(1L)
                        .name("12 ~ 2 실내")
                        .vote(vote)
                        .build(),
                VoteItem.builder()
                        .id(2L)
                        .name("11 ~ 1 야외")
                        .vote(vote)
                        .build());
        vote.addItems(voteItems);
        List<Vote> votes = List.of(vote);

        given(voteRepository.findAll()).willReturn(votes);
        List<VoteResponseDto> expectedResult = List.of(
                new VoteResponseDto(id, "12월 15일 풋살 투표", List.of(new VoteItemResponseDto(1L, "12 ~ 2 실내"), new VoteItemResponseDto(2L, "11 ~ 1 야외")))
        );

        //when
        List<VoteResponseDto> voteResponseDto = voteService.findAll();

        //then
        Assertions.assertThat(voteResponseDto).usingRecursiveComparison().isEqualTo(expectedResult);
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