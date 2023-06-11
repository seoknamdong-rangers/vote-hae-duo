package com.votehaeduo.dto.response;

import com.votehaeduo.dto.CommentPayload;
import com.votehaeduo.dto.MemberPayload;
import com.votehaeduo.dto.VoteItemPayload;
import com.votehaeduo.dto.VotePayload;
import com.votehaeduo.entity.Vote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateVoteResponse {

    private VotePayload votePayload;
    private String nickname;

    public static CreateVoteResponse of(Vote vote, MemberPayload memberPayload) {
        return CreateVoteResponse.builder()
                .votePayload(VotePayload.builder()
                        .id(vote.getId())
                        .title(vote.getTitle())
                        .startDate(vote.getStartDate())
                        .endDate(vote.getEndDate())
                        .createdMemberId(vote.getCreatedMemberId())
                        .voteItems(vote.getVoteItems().stream()
                                .map(VoteItemPayload::from)
                                .collect(Collectors.toList()))
                        .comments(vote.getComments().stream()
                                .map(CommentPayload::from)
                                .collect(Collectors.toList()))
                        .build()
                )
                .nickname(memberPayload.getNickname())
                .build();
    }

}
