package com.votehaeduo.dto.request;

import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteUpdateRequestDto {

    private String name;
    private List<VoteItemUpdateRequestDto> voteItems;

    public Vote toEntity() {
        Vote vote = Vote.builder()
                .name(name)
                .build();
        List<VoteItem> items = voteItems.stream()
                .map(voteItemUpdateRequestDto ->  voteItemUpdateRequestDto.toEntity(vote))
                .collect(Collectors.toList());
        vote.addItems(items);
        return vote;
    }

}
