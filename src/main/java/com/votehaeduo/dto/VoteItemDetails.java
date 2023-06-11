package com.votehaeduo.dto;

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
public class VoteItemDetails {

    private Long id;
    private String title;
    private Set<Long> memberIds;
    private Long voteItemMemberCount;

    public static VoteItemDetails from(VoteItem voteItem) {
        return new VoteItemDetails(
                voteItem.getId(),
                voteItem.getTitle(),
                voteItem.getMemberIds(),
                (long) voteItem.getMemberIds().size()
        );
    }

}
