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

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class VoteCreateResponseDtoTest {

    @Test
    @DisplayName("PostVoteResponseDto from 테스트")
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
                        .build(),
                VoteItem.builder()
                        .id(2L)
                        .vote(vote)
                        .title("item_title2")
                        .build());
        vote.addItems(voteItems);
        VoteCreateResponseDto expected = new VoteCreateResponseDto(new VotePayloadResponseDto(id, "title",
                LocalDate.of(2023, 1, 20),
                LocalDate.of(2023, 1, 25), "성준",
                List.of(new VoteItemPayloadResponseDto(1L, "item_title"),
                        new VoteItemPayloadResponseDto(2L, "item_title2"))));

        // when
        VoteCreateResponseDto result = VoteCreateResponseDto.from(vote);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

}