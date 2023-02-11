package com.votehaeduo.service;

import com.votehaeduo.dto.request.VoteItemUpdateRequestDto;
import com.votehaeduo.dto.request.VoteUpdateRequestDto;
import com.votehaeduo.dto.request.VoteItemSaveRequestDto;
import com.votehaeduo.dto.request.VoteSaveRequestDto;
import com.votehaeduo.dto.response.PostVoteResponseDto;
import com.votehaeduo.dto.response.VoteItemResponseDto;
import com.votehaeduo.dto.response.VoteResponseDto;
import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import com.votehaeduo.exception.vote.VoteNotFoundException;
import com.votehaeduo.repository.VoteRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class VoteServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @InjectMocks
    private VoteService voteService;

    @Test
    @DisplayName("투표 등록")
    void save() {
        // given
        Vote vote = Vote.builder()
                .id(1L)
                .title("1월 9일 풋살 투표")
                .startDate(LocalDate.of(2023, 2, 9))
                .endDate(LocalDate.of(2023, 2, 19))
                .createdBy("성준")
                .build();
        List<VoteItem> voteItems = List.of(VoteItem.builder()
                        .id(1L)
                        .title("11시 ~ 1시 실외")
                        .vote(vote)
                        .memberIds(Set.of(1L, 2L))
                        .build(),
                VoteItem.builder()
                        .id(2L)
                        .title("12시 ~ 2시 실내")
                        .vote(vote)
                        .memberIds(Set.of(1L, 2L))
                        .build());
        vote.addItems(voteItems);
        PostVoteResponseDto expectedResult = PostVoteResponseDto.from(vote);
        given(voteRepository.save(any())).willReturn(vote);

        // when
        PostVoteResponseDto postVoteResponseDto = voteService.save(VoteSaveRequestDto.builder()
                .title("1월 9일 풋살 투표")
                .startDate(LocalDate.of(2023, 2, 9))
                .endDate(LocalDate.of(2023, 2, 19))
                .createdBy("성준")
                .voteItems(List.of(
                        VoteItemSaveRequestDto.builder().title("11시 ~ 1시 실외").build(),
                        VoteItemSaveRequestDto.builder().title("12시 ~ 2시 실내").build())
                ).build());

        // then
        Assertions.assertThat(postVoteResponseDto).usingRecursiveComparison().isEqualTo(expectedResult);

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
        // given
        Long id = new Random().nextLong();
        Vote vote = Vote.builder()
                .id(id)
                .title("12월 15일 풋살 투표")
                .startDate(LocalDate.of(2023, 1, 20))
                .endDate(LocalDate.of(2023, 1, 25))
                .createdBy("성준")
                .build();
        List<VoteItem> voteItems = List.of(VoteItem.builder()
                        .id(1L)
                        .title("12 ~ 2 실내")
                        .vote(vote)
                        .memberIds(Set.of(1L, 2L))
                        .build(),
                VoteItem.builder()
                        .id(2L)
                        .title("11 ~ 1 야외")
                        .vote(vote)
                        .memberIds(Set.of(3L, 4L))
                        .build());
        vote.addItems(voteItems);
        given(voteRepository.findById(any())).willReturn(Optional.of(vote));
        VoteResponseDto expectedResult = new VoteResponseDto(id, "1월 15일 풋살 투표",
                LocalDate.of(2023, 1, 20),
                LocalDate.of(2023, 1, 25), "성준",
                List.of(new VoteItemResponseDto(1L, "11시 ~ 1시 실외", Set.of(1L, 2L)),
                        new VoteItemResponseDto(2L, "12시 ~ 2시 실내", Set.of(3L, 4L))));
        given(voteRepository.save(any())).willReturn(vote);

        // when
        VoteResponseDto voteResponseDto = voteService.update(id, VoteUpdateRequestDto.builder()
                .title("1월 15일 풋살 투표")
                .startDate(LocalDate.of(2023, 1, 20))
                .endDate(LocalDate.of(2023, 1, 25))
                .createdBy("성준")
                .voteItems(List.of(
                        VoteItemUpdateRequestDto.builder().id(1L).title("11시 ~ 1시 실외").memberIds(Set.of(1L, 2L)).build(),
                        VoteItemUpdateRequestDto.builder().id(2L).title("12시 ~ 2시 실내").memberIds(Set.of(3L, 4L)).build()))
                .build());

        // then
        Assertions.assertThat(voteResponseDto).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("투표 삭제")
    void delete() {
        // given
        given(voteRepository.findById(anyLong())).willReturn(Optional.of(Vote.builder().build()));
        doNothing().when(voteRepository).delete(any());

        // when
        boolean result = voteService.delete(new Random().nextLong());

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("투표 삭제 실패하는 경우")
    void deleteIfThrow() {
        // given
        given(voteRepository.findById(anyLong())).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> voteService.delete(new Random().nextLong()))
                .isInstanceOf(VoteNotFoundException.class)
                .hasMessage("해당 투표를 찾을 수 없습니다.");
    }

}