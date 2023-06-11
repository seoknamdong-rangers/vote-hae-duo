package com.votehaeduo.dto.request;

import com.votehaeduo.dto.CreateVoteItem;
import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreateVoteItemTest {

    @Test
    @DisplayName("VoteItemCreateRequestDto toEntity 테스트")
    void toEntity() {
        // given
        Vote vote = Vote.builder().build();
        CreateVoteItem createVoteItem = CreateVoteItem.builder()
                .title("9시 ~ 11시")
                .build();
        VoteItem expected = VoteItem.builder()
                .title("9시 ~ 11시")
                .vote(vote)
                .build();

        // when
        VoteItem result = createVoteItem.toEntity(vote);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

}