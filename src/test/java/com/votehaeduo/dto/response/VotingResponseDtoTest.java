package com.votehaeduo.dto.response;

import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class VotingResponseDtoTest {

    @Test
    void of() {
        // given
        Long id = new Random().nextLong();
        Vote vote = Vote.builder()
                .id(id)
                .title("title")
                .startDate(LocalDate.of(2023, 1, 20))
                .endDate(LocalDate.of(2023, 1, 25))
                .createdBy("성준")
                .build();
        List<VoteItem> voteItems = List.of(VoteItem.builder()
                        .id(1L)
                        .vote(vote)
                        .title("item_title")
                        .build(),
                VoteItem.builder()
                        .id(2L)
                        .vote(vote)
                        .title("item_title2")
                        .build());
        vote.addItems(voteItems);

        VoteMemberResponseDto uniqueCount = new VoteMemberResponseDto(Set.of(1L, 2L, 3L), 3L);

        List<VoteMemberResponseDto> uniqueCountByVoteItem = List.of(
                new VoteMemberResponseDto(Set.of(1L, 2L), 2L),
                new VoteMemberResponseDto(Set.of(3L), 1L));

        VotingResponseDto expected = new VotingResponseDto(
                new VotePayloadResponseDto(id, "title",
                        LocalDate.of(2023, 1, 20),
                        LocalDate.of(2023, 1, 25), "성준",
                        List.of(new VoteItemPayloadResponseDto(1L, "item_title"),
                                new VoteItemPayloadResponseDto(2L, "item_title2"))),
                new VoteMemberResponseDto(Set.of(1L, 2L, 3L), 3L),
                List.of(new VoteMemberResponseDto(Set.of(1L, 2L), 2L),
                        new VoteMemberResponseDto(Set.of(3L), 1L)));

        // when
        VotingResponseDto result = VotingResponseDto.of(vote, uniqueCount, uniqueCountByVoteItem);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

}