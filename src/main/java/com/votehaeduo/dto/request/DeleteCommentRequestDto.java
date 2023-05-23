package com.votehaeduo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteCommentRequestDto {

    @NotNull(message = "댓글 정보를 입력 해 주세요.")
    private Long commentId;
    @NotNull(message = "사용자 id는 필수 입니다.")
    private Long memberId;

}
