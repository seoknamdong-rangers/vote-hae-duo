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
class FindVoteItemResponseDtoTest {

    @Test
    @DisplayName("voteItemResponseDto 테스트")
    void from() {
        //given
        Long id = new Random().nextLong();
        VoteItem voteItems = VoteItem.builder()
                .id(id)
                .title("item_name1")
                .memberIds(Set.of(1L, 2L))
                .build();
        FindVoteItemResponseDto expected = new FindVoteItemResponseDto(id, "item_name1", Set.of(1L, 2L));

        //when
        FindVoteItemResponseDto result = FindVoteItemResponseDto.from(voteItems);

        //then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

}