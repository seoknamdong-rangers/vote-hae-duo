package com.votehaeduo.service;

import com.votehaeduo.dto.request.*;
import com.votehaeduo.dto.response.*;
import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import com.votehaeduo.exception.date.InvalidEndDateException;
import com.votehaeduo.exception.vote.VoteNotFoundException;
import com.votehaeduo.repository.VoteRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
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
        VoteCreateResponseDto expectedResult = VoteCreateResponseDto.from(vote);
        given(voteRepository.save(any())).willReturn(vote);

        // when
        VoteCreateResponseDto voteCreateResponseDto = voteService.create(VoteCreateRequestDto.builder()
                .title("1월 9일 풋살 투표")
                .startDate(LocalDate.of(2023, 2, 9))
                .endDate(LocalDate.of(2023, 2, 19))
                .createdBy("성준")
                .voteItems(List.of(
                        VoteItemCreateRequestDto.builder().title("11시 ~ 1시 실외").build(),
                        VoteItemCreateRequestDto.builder().title("12시 ~ 2시 실내").build())
                ).build());

        // then
        Assertions.assertThat(voteCreateResponseDto).usingRecursiveComparison().isEqualTo(expectedResult);

    }

    @Test
    @DisplayName("투표 전체 조회")
    void findAll() {
        // given
        Long id = new Random().nextLong();
        Vote vote = Vote.builder()
                .id(id)
                .title("12월 15일 풋살 투표")
                .startDate(LocalDate.of(2023, 1, 20))
                .endDate(LocalDate.of(2023, 1, 30))
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
                        .memberIds(Set.of(1L, 2L))
                        .build());
        vote.addItems(voteItems);
        List<Vote> votes = List.of(vote);
        given(voteRepository.findAll()).willReturn(votes);
        List<FindVoteResponseDto> expectedResult = List.of(new FindVoteResponseDto(id, "12월 15일 풋살 투표",
                LocalDate.of(2023, 1, 20),
                LocalDate.of(2023, 1, 30), "성준",
                List.of(new FindVoteItemResponseDto(1L, "12 ~ 2 실내", Set.of(1L, 2L)),
                        new FindVoteItemResponseDto(2L, "11 ~ 1 야외", Set.of(1L, 2L))), 2L)
        );

        // when
        List<FindVoteResponseDto> voteResponseDto = voteService.findAll();

        // then
        Assertions.assertThat(voteResponseDto).usingRecursiveComparison().isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("투표 상세 조회")
    void findByIdVote() {
        // given
        Long id = new Random().nextLong();
        Vote vote = Vote.builder()
                .id(id)
                .title("12월 15일 풋살 투표")
                .startDate(LocalDate.of(2023, 1, 20))
                .endDate(LocalDate.of(2023, 1, 30))
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
                        .memberIds(Set.of(1L, 2L))
                        .build());
        vote.addItems(voteItems);
        VoteResponseDto expectedVoteResponseDto = VoteResponseDto.from(vote);
        given(voteRepository.findById(any())).willReturn(Optional.of(vote));

        // when
        VoteResponseDto voteResponseDto = voteService.findById(id);

        // then
        Assertions.assertThat(voteResponseDto).usingRecursiveComparison().isEqualTo(expectedVoteResponseDto);
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
        VoteResponseDto expectedVoteResponseDto = new VoteResponseDto(id, "1월 15일 풋살 투표",
                LocalDate.of(2023, 1, 20),
                LocalDate.of(2023, 1, 25), "성준",
                List.of(new VoteItemResponseDto(1L, "11시 ~ 1시 실외", Set.of(1L, 2L), 2L),
                        new VoteItemResponseDto(2L, "12시 ~ 2시 실내", Set.of(3L, 4L), 2L)),
                4L);
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
        Assertions.assertThat(voteResponseDto).usingRecursiveComparison().isEqualTo(expectedVoteResponseDto);
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
    @DisplayName("투표 삭제 실패")
    void deleteIfThrow() {
        // given
        given(voteRepository.findById(anyLong())).willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(() -> voteService.delete(new Random().nextLong()))
                .isInstanceOf(VoteNotFoundException.class)
                .hasMessage("해당 투표를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("투표 하기")
    void voting() {
        // given
        Set<Long> firstMemberIds = new HashSet<>();
        firstMemberIds.add(1L);
        firstMemberIds.add(2L);
        Set<Long> secondMemberIds = new HashSet<>();
        secondMemberIds.add(1L);
        Long id = new Random().nextLong();
        Vote vote = Vote.builder()
                .id(id)
                .title("1월 8일 풋살")
                .startDate(LocalDate.of(2023, 1, 20))
                .endDate(LocalDate.of(2023, 1, 30))
                .createdBy("성준")
                .build();
        List<VoteItem> voteItems = List.of(VoteItem.builder()
                        .id(1L)
                        .title("11시 ~ 1시 실외")
                        .vote(vote)
                        .memberIds(firstMemberIds)
                        .build(),
                VoteItem.builder()
                        .id(2L)
                        .title("12시 ~ 2시 실내")
                        .vote(vote)
                        .memberIds(secondMemberIds)
                        .build());
        vote.addItems(voteItems);
        given(voteRepository.findById(any())).willReturn(Optional.of(vote));
        VotingResponseDto expectedVotingResponseDto = new VotingResponseDto(new VotePayloadResponseDto(id, "1월 8일 풋살",
                LocalDate.of(2023, 1, 20),
                LocalDate.of(2023, 1, 30), "성준",
                List.of(new VoteItemPayloadResponseDto(1L, "11시 ~ 1시 실외"),
                        new VoteItemPayloadResponseDto(2L, "12시 ~ 2시 실내"))),
                new VoteMemberResponseDto(Set.of(1L, 2L, 7L), 3L),
                List.of(new VoteMemberResponseDto(Set.of(1L, 2L, 7L), 3L),
                        new VoteMemberResponseDto(Set.of(1L), 1L)));
        given(voteRepository.save(any())).willReturn(vote);

        // when
        VotingResponseDto votingResponseDto = voteService.voting(id, new VotingRequestDto(7L, List.of(1L)));

        // then
        Assertions.assertThat(votingResponseDto).usingRecursiveComparison().isEqualTo(expectedVotingResponseDto);
    }

    @Test
    @DisplayName("투표 하기 실패")
    void BadVoting() {
        // given
        Long voteId = 1L;
        LocalDateTime expiredDateTime = LocalDateTime.now().minusDays(1); // 1일 전에 만료된 투표
        Vote vote = Vote.builder()
                .id(voteId)
                .title("투표 제목")
                .startDate(LocalDate.from(LocalDateTime.now().minusDays(2)))
                .endDate(LocalDate.from(expiredDateTime))
                .build();
        VotingRequestDto requestDto = new VotingRequestDto(1L, List.of(1L, 2L));

        given(voteRepository.findById(voteId)).willReturn(Optional.of(vote));

        // when, then
        assertThatThrownBy(() -> voteService.voting(voteId, requestDto))
                .isInstanceOf(InvalidEndDateException.class)
                .hasMessage("투표 마감일을 초과한 날짜로 투표를 진행할 수 없습니다.");
    }

}