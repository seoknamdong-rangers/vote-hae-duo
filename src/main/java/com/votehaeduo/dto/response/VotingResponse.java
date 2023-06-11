package com.votehaeduo.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.votehaeduo.dto.CommentPayload;
import com.votehaeduo.dto.VoteItemDetails;
import com.votehaeduo.entity.Vote;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VotingResponse {

    private Long id;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate endDate;
    private Long createdMemberId;
    private List<VoteItemDetails> voteItems;
    private List<CommentPayload> voteComments;
    private Set<Long> voteMemberIds;
    private Long voteMemberCount;

    public static VotingResponse of(Vote vote, Set<Long> voteMemberIds) {
        return new VotingResponse(
                vote.getId(),
                vote.getTitle(),
                vote.getStartDate(),
                vote.getEndDate(),
                vote.getCreatedMemberId(),
                vote.getVoteItems().stream()
                        .map(VoteItemDetails::from)
                        .collect(Collectors.toList()),
                vote.getComments().stream()
                        .map(CommentPayload::from)
                        .collect(Collectors.toList()),
                voteMemberIds,
                (long) voteMemberIds.size());
    }

}
