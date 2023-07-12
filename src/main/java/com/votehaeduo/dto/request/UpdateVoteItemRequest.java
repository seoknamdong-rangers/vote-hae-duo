package com.votehaeduo.dto.request;

import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateVoteItemRequest { // 삭제 예정

    private Long id;
    private String title;
    private Set<Long> memberIds;

    public VoteItem toEntity(Vote vote) {
        return VoteItem.builder()
                .id(id)
                .title(title)
                .vote(vote)
                .memberIds(memberIds)
                .build();
    }

}
