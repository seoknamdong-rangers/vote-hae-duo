package com.votehaeduo.dto.response;

import com.votehaeduo.entity.VoteItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class PostVoteItemResponseDtoTest {

    @Test
    @DisplayName("PostVoteItemResponseDto from 테스트")
    void from() {
        // given
        Long id = new Random().nextLong();
        VoteItem voteItems = VoteItem.builder()
                .id(id)
                .title("item_name1")
                .memberIds(Set.of(1L))
                .build();
        PostVoteItemResponseDto expected = new PostVoteItemResponseDto(id, "item_name1");

        // when
        PostVoteItemResponseDto result = PostVoteItemResponseDto.from(voteItems);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

}