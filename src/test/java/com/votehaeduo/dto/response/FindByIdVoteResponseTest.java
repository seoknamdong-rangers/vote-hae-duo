package com.votehaeduo.dto.response;

import com.votehaeduo.entity.Comment;
import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class FindByIdVoteResponseTest {

    @Test
    @DisplayName("FindByIdVoteResponse of Test")
    void of() {
        // given
        Long id = new Random().nextLong();
        Vote vote = Vote.builder()
                .id(id)
                .title("풋살")
                .startDate(LocalDate.of(2023, 5, 23))
                .endDate(LocalDate.of(2023, 5, 28))
                .createdBy("성준")
                .comments(List.of(Comment.builder()
                        .id(1L)
                        .content("정말 재밌다..")
                        .date(LocalDate.of(2023, 5, 23))
                        .createdBy("성준")
                        .memberId(1L)
                        .build()))
                .build();
        List<VoteItem> voteItems = List.of(VoteItem.builder()
                .id(1L)
                .title("item_name")
                .vote(vote)
                .memberIds(Set.of(1L, 2L))
                .build());
        vote.addItems(voteItems);
        FindByIdVoteResponse expected = new FindByIdVoteResponse(
                id, "풋살",
                LocalDate.of(2023, 5, 23),
                LocalDate.of(2023, 5, 28),
                "성준",
                List.of(new VoteItemResponseDto(1L, "item_name", Set.of(1L, 2L))),
                List.of(new FindCommentResponseDto(1L, "정말 재밌다..", "성준", LocalDate.of(2023, 5, 23))),
                2L);

        // when
        FindByIdVoteResponse result = FindByIdVoteResponse.of(vote, 2L);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

}