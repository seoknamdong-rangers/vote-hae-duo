package com.votehaeduo.dto.request;

import com.votehaeduo.entity.Vote;
import com.votehaeduo.entity.VoteItem;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class VoteItemSaveRequestDto {

    @NotBlank(message = "투표 항목의 제목은 공백이 아니어야 합니다.")
    private String title;

    public VoteItem toEntity(Vote vote) {
        return VoteItem.builder()
                .title(this.title)
                .vote(vote)
                .build();
    }

}
