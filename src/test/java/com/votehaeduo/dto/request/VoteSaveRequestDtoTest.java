package com.votehaeduo.dto.request;

import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class VoteSaveRequestDtoTest {

    @Test
    @DisplayName("VoteSaveRequestDtoTest toEntity 테스트")
    void toEntity() {
        // given
        VoteSaveRequestDto voteSaveRequestDto = new VoteSaveRequestDto("12월 28일 풋살 투표",
                List.of(new VoteItemSaveRequestDto("11시 ~ 1시"),
                        new VoteItemSaveRequestDto("12시 ~ 2시")));
        Vote expected = Vote.builder()
                .title("12월 28일 풋살 투표")
                .build();
        List<VoteItem> voteItems = List.of(VoteItem.builder()
                        .title("11시 ~ 1시")
                        .vote(expected)
                        .build(),
                VoteItem.builder()
                        .title("12시 ~ 2시")
                        .vote(expected)
                        .build());
        expected.addItems(voteItems);

        //when
        Vote result = voteSaveRequestDto.toEntity();

        //then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

}