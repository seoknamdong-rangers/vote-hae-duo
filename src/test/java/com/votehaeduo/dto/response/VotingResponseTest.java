package com.votehaeduo.dto.response;

import com.votehaeduo.dto.CommentPayload;
import com.votehaeduo.dto.VoteItemDetails;
import com.votehaeduo.entity.Comment;
import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class VotingResponseTest {

    @Test
    void of() {
        // given
        Long id = new Random().nextLong();
        List<Comment> comments = new ArrayList<>();
        comments.add(Comment.builder()
                .id(1L)
                .content("재밌겠다")
                .nickname("킴대세")
                .date(LocalDate.of(2023, 6, 8))
                .memberId(1L)
                .build());
        Vote vote = Vote.builder()
                .id(id)
                .title("title")
                .startDate(LocalDate.of(2023, 1, 20))
                .endDate(LocalDate.of(2023, 1, 25))
                .createdMemberId(1L)
                .comments(comments)
                .build();
        List<VoteItem> voteItems = List.of(VoteItem.builder()
                        .id(1L)
                        .vote(vote)
                        .title("item_title")
                        .memberIds(Set.of(1L, 2L))
                        .build(),
                VoteItem.builder()
                        .id(2L)
                        .vote(vote)
                        .title("item_title2")
                        .memberIds(Set.of(1L, 2L))
                        .build());
        vote.addItems(voteItems);

        VotingResponse expected = new VotingResponse(
                id, "title",
                LocalDate.of(2023, 1, 20),
                LocalDate.of(2023, 1, 25), 1L,
                List.of(new VoteItemDetails(1L, "item_title", Set.of(1L, 2L), 2L),
                        new VoteItemDetails(2L, "item_title2", Set.of(1L, 2L), 2L)),
                List.of(new CommentPayload(1L, "재밌겠다", "킴대세",
                        LocalDate.of(2023, 6, 8), 1L)),
                Set.of(1L, 2L), 2L);
        Set<Long> voteMemberIds = vote.getVoteParticipantsTotalMemberIds();

        // when
        VotingResponse result = VotingResponse.of(vote, voteMemberIds);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

}