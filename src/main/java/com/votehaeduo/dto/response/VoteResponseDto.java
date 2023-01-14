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
public class VoteResponseDto {

    private Long id;
    private String title;
    private List<VoteItemResponseDto> voteItems;

    public static VoteResponseDto from(Vote vote) {
        return new VoteResponseDto(
                vote.getId(),
                vote.getTitle(),
                vote.getVoteItems().stream()
                        .map(VoteItemResponseDto::from)
                        .collect(Collectors.toList())
        );
    }

}
