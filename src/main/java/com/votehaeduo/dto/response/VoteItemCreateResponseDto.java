package com.votehaeduo.dto.response;

import com.votehaeduo.entity.VoteItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VoteItemCreateResponseDto {

    private Long id;
    private String title;

    public static VoteItemCreateResponseDto from(VoteItem voteItem) {
        return new VoteItemCreateResponseDto(
                voteItem.getId(),
                voteItem.getTitle()
        );
    }

}
