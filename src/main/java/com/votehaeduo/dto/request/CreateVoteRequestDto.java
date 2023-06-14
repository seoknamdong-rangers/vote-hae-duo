package com.votehaeduo.dto.request;

import com.votehaeduo.dto.CreateVoteItem;
import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import com.votehaeduo.entity.enumeration.VoteCreateOption;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
public class CreateVoteRequestDto {

    @NotBlank(message = "제목은 공백이 아니어야 합니다.")
    private String title;
    @FutureOrPresent(message = "투표 등록 일자는 오늘기준 이후 날짜를 선택해야합니다.")
    private LocalDate startDate;
    @Future(message = "투표 종료 일자는 오늘기준 이후 날짜를 선택해야합니다.")
    private LocalDate endDate;
    @NotNull(message = "작성자는 공백이 아니어야 합니다.")
    private Long createdMemberId;
    @NotEmpty(message = "투표 항목은 공백이 아니어야 합니다.")
    private List<CreateVoteItem> voteItems;

    private VoteCreateOption voteCreateOption;

    public CreateVoteRequestDto(String title, LocalDate startDate, LocalDate endDate, Long createdMemberId, List<CreateVoteItem> voteItems, VoteCreateOption voteCreateOption) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdMemberId = createdMemberId;
        this.voteItems = voteItems;
        this.voteCreateOption = voteCreateOption;
    }

    public CreateVoteRequestDto(String title, LocalDate startDate, LocalDate endDate, Long createdMemberId, List<CreateVoteItem> voteItems) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdMemberId = createdMemberId;
        this.voteItems = voteItems;
        this.voteCreateOption = VoteCreateOption.MANUAL;
    }

    public Vote toEntity() {
        Vote vote = Vote.builder()
                .title(this.title)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .createdMemberId(this.createdMemberId)
                .voteCreateOption(this.voteCreateOption)
                .build();
        List<VoteItem> items = voteItems.stream()
                .map(voteItemCreateRequestDto -> voteItemCreateRequestDto.toEntity(vote))
                .collect(Collectors.toList());
        vote.addItems(items);
        return vote;
    }

}