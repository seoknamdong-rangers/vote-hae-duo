package com.votehaeduo.dto.response;

import com.votehaeduo.dto.VoteItemDetails;
import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class FindUpdateVoteResponseTest {

    @Test
    @DisplayName("voteResponseDto 생성 테스트")
    void of() {
        // given
        Long id = new Random().nextLong();
        Vote vote = Vote.builder()
                .id(id)
                .title("name")
                .startDate(LocalDate.of(2023, 1, 20))
                .endDate(LocalDate.of(2023, 1, 30))
                .createdMemberId(1L)
                .build();
        List<VoteItem> voteItems = List.of(VoteItem.builder()
                        .id(1L)
                        .title("item_name")
                        .vote(vote)
                        .memberIds(Set.of(1L, 2L))
                        .build(),
                VoteItem.builder()
                        .id(2L)
                        .title("item_name2")
                        .vote(vote)
                        .memberIds(Set.of(1L, 2L))
                        .build());
        vote.addItems(voteItems);
        FindVoteResponse expected = new FindVoteResponse(id, "name",
                LocalDate.of(2023, 1, 20),
                LocalDate.of(2023, 1, 30), 1L,
                List.of(new VoteItemDetails(1L, "item_name", Set.of(1L, 2L), 2L),
                        new VoteItemDetails(2L, "item_name2", Set.of(1L, 2L),2L)), 10L
        );

        // when
        FindVoteResponse result = FindVoteResponse.of(vote, 10L);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

}