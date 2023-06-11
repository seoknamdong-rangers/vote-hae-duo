package com.votehaeduo.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.votehaeduo.dto.CommentPayload;
import com.votehaeduo.dto.VoteItemDetails;
import com.votehaeduo.entity.Vote;
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
public class FindByIdVoteResponse {

    private Long id;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate endDate;
    private Long createdMemberId;
    private List<VoteItemDetails> voteItems;
    private List<CommentPayload> comments;
    private Long uniqueCount;

    public static FindByIdVoteResponse of(Vote vote, Long uniqueCount) {
        return FindByIdVoteResponse.builder()
                .id(vote.getId())
                .title(vote.getTitle())
                .startDate(vote.getStartDate())
                .endDate(vote.getEndDate())
                .createdMemberId(vote.getCreatedMemberId())
                .voteItems(vote.getVoteItems().stream()
                        .map(VoteItemDetails::from)
                        .collect(Collectors.toList()))
                .comments(vote.getComments().stream()
                        .map(CommentPayload::from)
                        .collect(Collectors.toList()))
                .uniqueCount(uniqueCount)
                .build();
    }

}
