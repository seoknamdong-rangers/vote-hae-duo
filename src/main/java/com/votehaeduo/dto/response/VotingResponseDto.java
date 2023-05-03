package com.votehaeduo.dto.response;

import com.votehaeduo.entity.Vote;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VotingResponseDto {

    private VotePayloadResponseDto votePayloadResponseDto;
    private VoteMemberResponseDto uniqueCount;
    private List<VoteMemberResponseDto> uniqueCountByVoteItem;

    public static VotingResponseDto of(Vote vote, VoteMemberResponseDto uniqueCount,
                                       List<VoteMemberResponseDto> uniqueCountByVoteItem) {
        return new VotingResponseDto(
                new VotePayloadResponseDto(
                        vote.getId(),
                        vote.getTitle(),
                        vote.getStartDate(),
                        vote.getEndDate(),
                        vote.getCreatedBy(),
                        vote.getVoteItems().stream()
                                .map(VoteItemPayloadResponseDto::from)
                                .collect(Collectors.toList())),
                uniqueCount,
                uniqueCountByVoteItem);
    }

}
