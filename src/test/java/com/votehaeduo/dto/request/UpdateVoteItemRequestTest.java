package com.votehaeduo.dto.request;

import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateVoteItemRequestTest {

    @Test
    @DisplayName("VoteItemUpdateRequestDto toEntity 테스트")
    void toEntity() {
        // given
        Vote vote = Vote.builder().build();
        UpdateVoteItemRequest updateVoteItemRequest = UpdateVoteItemRequest.builder()
                .id(1L)
                .title("9시 ~ 11시")
                .memberIds(Set.of(1L, 2L))
                .build();
        VoteItem expected = VoteItem.builder()
                .id(1L)
                .title("9시 ~ 11시")
                .vote(vote)
                .memberIds(Set.of(1L, 2L))
                .build();

        // when
        VoteItem result = updateVoteItemRequest.toEntity(vote);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

}