package com.votehaeduo.dto.response;

import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
                .title("title")
                .startDate(LocalDate.of(2023, 1, 20))
                .endDate(LocalDate.of(2023, 1, 25))
                .createdBy("성준")
                .build();
        List<VoteItem> voteItems = List.of(VoteItem.builder()
                        .id(1L)
                        .vote(vote)
                        .title("item_title")
                        .memberIds(Set.of(1L))
                        .build(),
                VoteItem.builder()
                        .id(2L)
                        .vote(vote)
                        .title("item_title2")
                        .memberIds(Set.of(1L))
                        .build());
        vote.addItems(voteItems);
        VoteResponseDto expected = new VoteResponseDto(id, "title",
                LocalDate.of(2023, 1, 20),
                LocalDate.of(2023, 1, 25), "성준",
                List.of(new VoteItemResponseDto(1L, "item_name", Set.of(1L)),
                        new VoteItemResponseDto(2L, "item_name2", Set.of(1L))));

        // when
        VoteResponseDto result = VoteResponseDto.from(vote);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

}