package com.votehaeduo.dto.response;

import com.votehaeduo.dto.CommentPayload;
import com.votehaeduo.dto.MemberPayload;
import com.votehaeduo.dto.VoteItemPayload;
import com.votehaeduo.dto.VotePayload;
import com.votehaeduo.entity.Comment;
import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CreateVoteResponseTest {

    @Test
    @DisplayName("PostVoteResponseDto of 테스트")
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
        CreateVoteResponse expected = new CreateVoteResponse(new VotePayload(id, "title",
                LocalDate.of(2023, 1, 20),
                LocalDate.of(2023, 1, 25), 1L,
                List.of(new VoteItemPayload(1L, "item_title", Set.of(1L, 2L)),
                        new VoteItemPayload(2L, "item_title2", Set.of(1L, 2L))),
                List.of(new CommentPayload(1L, "재밌겠다", "킴대세",
                        LocalDate.of(2023, 6, 8), 1L))), "킴대세");

        // when
        CreateVoteResponse result = CreateVoteResponse.of(vote, MemberPayload.builder()
                .id(1L)
                .kakaoMemberNo("a")
                .nickname("킴대세")
                .email("b")
                .profileUrl("c")
                .build());

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

}