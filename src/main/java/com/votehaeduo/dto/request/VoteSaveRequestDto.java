package com.votehaeduo.dto.request;

import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteSaveRequestDto {

    private String name;
    private List<VoteItemSaveRequestDto> voteItems;

    public Vote toEntity() {
        Vote vote = Vote.builder()
                .name(name)
                .build();
        List<VoteItem> items = voteItems.stream()
                .map(voteItemSaveRequestDto -> voteItemSaveRequestDto.toEntity(vote))
                .collect(Collectors.toList());
        vote.addItems(items);
        return vote;
    }

}