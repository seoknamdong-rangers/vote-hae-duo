package com.votehaeduo.dto.request;

import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class VoteUpdateRequestDtoTest {

    @Test
    @DisplayName("VoteSaveRequestDtoTest toEntity 테스트")
    void toEntity() {
        // given
        VoteUpdateRequestDto voteUpdateRequestDto = new VoteUpdateRequestDto("12월 28일 풋살 투표",
                LocalDate.of(2023, 1, 20),
                LocalDate.of(2023, 1, 25),
                "성준",
                List.of(new VoteItemUpdateRequestDto(1L, "11시 ~ 1시"),
                        new VoteItemUpdateRequestDto(2L, "12시 ~ 2시")));
        Vote expected = Vote.builder()
                .title("12월 28일 풋살 투표")
                .startDate(LocalDate.of(2023, 1, 20))
                .endDate(LocalDate.of(2023, 1, 25))
                .createdBy("성준")
                .build();
        List<VoteItem> voteItems = List.of(VoteItem.builder()
                        .id(1L)
                        .title("11시 ~ 1시")
                        .vote(expected)
                        .build(),
                VoteItem.builder()
                        .id(2L)
                        .title("12시 ~ 2시")
                        .vote(expected)
                        .build());
        expected.addItems(voteItems);

        // when
        Vote result = Vote.builder()
                .title(voteUpdateRequestDto.getTitle())
                .startDate(voteUpdateRequestDto.getStartDate())
                .endDate(voteUpdateRequestDto.getEndDate())
                .createdBy(voteUpdateRequestDto.getCreatedBy())
                .build();
        List<VoteItem> items = voteUpdateRequestDto.getVoteItems().stream()
                .map(voteItemUpdateRequestDto -> voteItemUpdateRequestDto.toEntity(result))
                .collect(Collectors.toList());
        result.setVoteItems(items);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

}