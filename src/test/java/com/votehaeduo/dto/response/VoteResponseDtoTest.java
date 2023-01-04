package com.votehaeduo.dto.response;

import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class VoteResponseDtoTest {

    @Test
    @DisplayName("voteResponseDto 생성 테스트")
    void from() {

        // given
        Long id = new Random().nextLong();
        Vote vote = Vote.builder()
                .id(id)
                .name("name")
                .build();
        List<VoteItem> voteItems = List.of(VoteItem.builder()
                        .id(1L)
                        .name("item_name")
                        .vote(vote)
                        .build(),
                VoteItem.builder()
                        .id(2L)
                        .name("item_name2")
                        .vote(vote)
                        .build());
        vote.addItems(voteItems);
        VoteResponseDto expected = new VoteResponseDto(id, "name", List.of(
                new VoteItemResponseDto(1L, "item_name"), new VoteItemResponseDto(2L, "item_name2")));

        // when
        VoteResponseDto result = VoteResponseDto.from(vote);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

}