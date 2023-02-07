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
public class VoteItemUpdateRequestDto {

    private Long id;
    private String title;
    private Set<Long> memberIds;

    public VoteItem toEntity(Vote vote) { // 필드에 memberIds 를 만들까?
        return VoteItem.builder()
                .id(id)
                .title(title)
                .vote(vote)
                .memberIds(memberIds)
                .build();
    }

}
