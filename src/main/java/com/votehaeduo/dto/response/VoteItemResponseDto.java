package com.votehaeduo.dto.response;

import com.votehaeduo.entity.VoteItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VoteItemResponseDto {

    private Long id;
    private String name;

    public static VoteItemResponseDto from(VoteItem voteItem) {
        return new VoteItemResponseDto(
                voteItem.getId(),
                voteItem.getName()
        );
    }

}
