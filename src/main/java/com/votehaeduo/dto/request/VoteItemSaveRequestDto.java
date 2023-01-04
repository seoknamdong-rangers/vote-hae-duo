package com.votehaeduo.dto.request;

import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import lombok.*;

@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class VoteItemSaveRequestDto {

    private String name;

    public VoteItem toEntity(Vote vote) {
        return VoteItem.builder()
                .name(name)
                .vote(vote)
                .build();
    }

}
