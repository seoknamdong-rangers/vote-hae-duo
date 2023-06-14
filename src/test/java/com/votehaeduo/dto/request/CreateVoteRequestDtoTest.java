package com.votehaeduo.dto.request;

import com.votehaeduo.dto.CreateVoteItem;
import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import com.votehaeduo.entity.enumeration.VoteCreateOption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CreateVoteRequestDtoTest {

    @Test
    @DisplayName("VoteCreateRequestDtoTest toEntity 테스트")
    void toEntity() {
        // given
        CreateVoteRequestDto createVoteRequestDto = new CreateVoteRequestDto("12월 28일 풋살 투표",
                LocalDate.of(2023, 2, 9),
                LocalDate.of(2023, 2, 19), 1L,
                List.of(new CreateVoteItem("11시 ~ 1시"),
                        new CreateVoteItem("12시 ~ 2시")), VoteCreateOption.MANUAL);
        Vote expected = Vote.builder()
                .title("12월 28일 풋살 투표")
                .startDate(LocalDate.of(2023, 2, 9))
                .endDate(LocalDate.of(2023, 2, 19))
                .createdMemberId(1L)
                .voteCreateOption(VoteCreateOption.MANUAL)
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

        // when
        Vote result = createVoteRequestDto.toEntity();

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

}