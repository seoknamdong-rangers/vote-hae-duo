package com.votehaeduo.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.votehaeduo.dto.VoteItemDetails;
import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateVoteResponse {

    private Long id;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate endDate;
    private Long createdMemberId;
    private List<VoteItemDetails> voteItems;
    private Long uniqueCount;

    public static UpdateVoteResponse from(Vote vote) {
        return new UpdateVoteResponse(
                vote.getId(),
                vote.getTitle(),
                vote.getStartDate(),
                vote.getEndDate(),
                vote.getCreatedMemberId(),
                vote.getVoteItems().stream()
                        .map(VoteItemDetails::from)
                        .collect(Collectors.toList()),
                (long) vote.getVoteItems().stream()
                        .map(VoteItem::getMemberIds)
                        .flatMap(Set::stream)
                        .collect(Collectors.toSet())
                        .size());
    }

}
