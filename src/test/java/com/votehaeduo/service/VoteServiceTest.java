package com.votehaeduo.service;

import com.votehaeduo.dto.response.GetVoteItemResponseDto;
import com.votehaeduo.dto.response.GetVoteResponseDto;
import com.votehaeduo.dto.request.VoteItemUpdateRequestDto;
import com.votehaeduo.dto.request.VoteUpdateRequestDto;
import com.votehaeduo.dto.request.VoteItemSaveRequestDto;
import com.votehaeduo.dto.request.VoteSaveRequestDto;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
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

    }

    @Test
    @DisplayName("투표 전체 조회")
    void findAll() {
        //given
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
        List<GetVoteResponseDto> expectedResult = List.of(new GetVoteResponseDto(id, "12월 15일 풋살 투표",
                LocalDate.of(2023, 1, 20),
                LocalDate.of(2023, 1, 30), "성준",
                List.of(new GetVoteItemResponseDto(1L, "12 ~ 2 실내", Set.of(1L, 2L)),
                        new GetVoteItemResponseDto(2L, "11 ~ 1 야외", Set.of(1L, 2L))), 2L)
        );

        //when
        List<GetVoteResponseDto> voteResponseDto = voteService.findAll();

        // then
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