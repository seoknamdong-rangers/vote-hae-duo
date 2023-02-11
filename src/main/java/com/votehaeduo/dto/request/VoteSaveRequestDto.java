package com.votehaeduo.dto.request;

import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteSaveRequestDto {

    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String createdBy;
    private List<VoteItemSaveRequestDto> voteItems;

    public Vote toEntity() {
        Vote vote = Vote.builder()
                .title(this.title)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .createdBy(this.createdBy)
                .build();
        List<VoteItem> items = voteItems.stream()
                .map(voteItemSaveRequestDto -> voteItemSaveRequestDto.toEntity(vote))
                .collect(Collectors.toList());
        vote.addItems(items);
        return vote;
    }

}