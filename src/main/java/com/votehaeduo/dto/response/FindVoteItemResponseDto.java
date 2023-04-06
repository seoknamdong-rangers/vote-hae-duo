package com.votehaeduo.dto.response;

import com.votehaeduo.entity.VoteItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindVoteItemResponseDto {

    private Long id;
    private String title;
    private Set<Long> memberIds;

    public static FindVoteItemResponseDto from(VoteItem voteItem) {
        return new FindVoteItemResponseDto(
                voteItem.getId(),
                voteItem.getTitle(),
                voteItem.getMemberIds()
        );
    }

}
