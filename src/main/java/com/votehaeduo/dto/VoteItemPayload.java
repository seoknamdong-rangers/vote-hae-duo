package com.votehaeduo.dto;

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
public class VoteItemPayload {

    private Long id;
    private String title;
    private Set<Long> memberIds;

    public static VoteItemPayload from(VoteItem voteItem) {
        return new VoteItemPayload(
                voteItem.getId(),
                voteItem.getTitle(),
                voteItem.getMemberIds()
        );
    }

    public VoteItem toEntity(Vote vote) {
        return VoteItem.builder()
                .id(id)
                .title(title)
                .vote(vote)
                .memberIds(memberIds)
                .build();
    }

}
