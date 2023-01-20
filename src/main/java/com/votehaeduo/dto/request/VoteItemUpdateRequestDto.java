package com.votehaeduo.dto.request;

import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteItemUpdateRequestDto {

    private Long id;
    private String title;

    public VoteItem toEntity(Vote vote) {
        return VoteItem.builder()
                .id(id)
                .title(title)
                .vote(vote)
                .build();
    }

}
