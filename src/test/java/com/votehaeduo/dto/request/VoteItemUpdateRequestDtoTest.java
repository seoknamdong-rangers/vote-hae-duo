package com.votehaeduo.dto.request;

import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VoteItemUpdateRequestDtoTest {

    @Test
    @DisplayName("VoteItemUpdateRequestDto toEntity 테스트")
    void toEntity() {
        // given
        Vote vote = Vote.builder().build();
        VoteItemUpdateRequestDto voteItemUpdateRequestDto = VoteItemUpdateRequestDto.builder()
                .id(1L)
                .title("9시 ~ 11시")
                .build();
        VoteItem expected = VoteItem.builder()
                .id(1L)
                .title("9시 ~ 11시")
                .vote(vote)
                .build();

        // when
        VoteItem result = voteItemUpdateRequestDto.toEntity(vote);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

}