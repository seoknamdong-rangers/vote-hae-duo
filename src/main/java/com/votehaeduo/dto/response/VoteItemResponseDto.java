package com.votehaeduo.dto.response;

import com.votehaeduo.entity.VoteItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VoteItemResponseDto {

    private Long id;
    private String title;
    private Set<Long> memberIds;
    private Long uniqueCount;

    public static VoteItemResponseDto from(VoteItem voteItem) {
        return new VoteItemResponseDto(
                voteItem.getId(),
                voteItem.getTitle(),
                voteItem.getMemberIds(),
                (long) voteItem.getMemberIds().size()
        );
    }

}
