package com.votehaeduo.service;

import com.votehaeduo.dto.request.VoteItemUpdateRequestDto;
import com.votehaeduo.dto.request.VoteUpdateRequestDto;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

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
                .title("1월 9일 풋살 투표")
                .build();
        List<VoteItem> voteItems = List.of(VoteItem.builder()
                        .id(1L)
                        .title("11시 ~ 1시 실외")
                        .vote(vote)
                        .build(),
                VoteItem.builder()
                        .id(2L)
                        .title("12시 ~ 2시 실내")
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

    }

    @Test
    void findById() {
    }

    @Test
    @DisplayName("투표 수정")
    void update() {
        //given
        Vote vote = Vote.builder()
                .id(1L)
                .title("12월 15일 풋살 투표")
                .startDate(LocalDate.of(2023, 1, 20))
                .endDate(LocalDate.of(2023, 1, 25))
                .createdBy("성준")
                .build();
        List<VoteItem> voteItems = List.of(VoteItem.builder()
                        .id(1L)
                        .title("12 ~ 2 실내")
                        .vote(vote)
                        .build(),
                VoteItem.builder()
                        .id(2L)
                        .title("11 ~ 1 야외")
                        .vote(vote)
                        .build());
        vote.addItems(voteItems);
        given(voteRepository.findById(any())).willReturn(Optional.of(vote));
        VoteResponseDto expectedResult = new VoteResponseDto(1L, "1월 15일 풋살 투표",
                LocalDate.of(2023, 1, 20),
                LocalDate.of(2023, 1, 25), "성준",
                List.of(new VoteItemResponseDto(1L, "11시 ~ 1시 실외", Set.of(1L)),
                        new VoteItemResponseDto(2L, "12시 ~ 2시 실내", Set.of(1L))));
        voteRepository.save(vote);

        //when
        VoteResponseDto voteResponseDto = voteService.update(1L, VoteUpdateRequestDto.builder()
                .title("1월 15일 풋살 투표")
                .voteItems(List.of(
                        VoteItemUpdateRequestDto.builder().id(1L).title("11시 ~ 1시 실외").build(),
                        VoteItemUpdateRequestDto.builder().id(2L).title("12시 ~ 2시 실내").build()))
                .build());

        //then
        Assertions.assertThat(voteResponseDto).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    void delete() {
    }
}