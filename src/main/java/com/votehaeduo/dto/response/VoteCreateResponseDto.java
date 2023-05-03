package com.votehaeduo.dto.response;

import com.votehaeduo.entity.Vote;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VoteCreateResponseDto {

    private VotePayloadResponseDto votePayloadResponseDto;

    public static VoteCreateResponseDto from(Vote vote) {
        return new VoteCreateResponseDto(
                new VotePayloadResponseDto(
                        vote.getId(),
                        vote.getTitle(),
                        vote.getStartDate(),
                        vote.getEndDate(),
                        vote.getCreatedBy(),
                        vote.getVoteItems().stream()
                                .map(VoteItemPayloadResponseDto::from)
                                .collect(Collectors.toList())
                )
        );
    }

}
