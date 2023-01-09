package com.votehaeduo.dto.response;

import com.votehaeduo.entity.VoteItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class VoteItemResponseDtoTest {

    @Test
    @DisplayName("voteItemResponseDto 테스트")
    void from() {
        //given
        Long id = new Random().nextLong();
        VoteItem voteItems = VoteItem.builder()
                        .id(id)
                        .name("item_name1")
                        .build();
        VoteItemResponseDto expected = new VoteItemResponseDto(id, "item_name1");

        //when
        VoteItemResponseDto result = VoteItemResponseDto.from(voteItems);

        //then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

}