package com.votehaeduo.dto.request;

import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class VoteUpdateRequestDtoTest {

    @Test
    @DisplayName("VoteSaveRequestDtoTest toEntity 테스트")
    void toEntity() {
        // given
        VoteUpdateRequestDto voteUpdateRequestDto = new VoteUpdateRequestDto("12월 28일 풋살 투표",
                List.of(new VoteItemUpdateRequestDto(1L, "11시 ~ 1시"),
                        new VoteItemUpdateRequestDto(2L, "12시 ~ 2시")));
        Vote expected = Vote.builder()
                .name("12월 28일 풋살 투표")
                .build();
        List<VoteItem> voteItems = List.of(VoteItem.builder()
                        .id(1L)
                        .name("11시 ~ 1시")
                        .vote(expected)
                        .build(),
                VoteItem.builder()
                        .id(2L)
                        .name("12시 ~ 2시")
                        .vote(expected)
                        .build());
        expected.addItems(voteItems);

        //when
        Vote result = voteUpdateRequestDto.toEntity();

        //then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

}