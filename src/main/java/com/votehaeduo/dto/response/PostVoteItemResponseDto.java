package com.votehaeduo.dto.response;

import com.votehaeduo.entity.VoteItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostVoteItemResponseDto {

    private Long id;
    private String title;

    public static PostVoteItemResponseDto from(VoteItem voteItem) {
        return new PostVoteItemResponseDto(
                voteItem.getId(),
                voteItem.getTitle()
        );
    }

}
