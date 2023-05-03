package com.votehaeduo.dto.response;

import com.votehaeduo.entity.VoteItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VoteItemPayloadResponseDto {

    private Long id;
    private String title;

    public static VoteItemPayloadResponseDto from(VoteItem voteItem) {
        return new VoteItemPayloadResponseDto(
                voteItem.getId(),
                voteItem.getTitle()
        );
    }

}
