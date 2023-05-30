package com.votehaeduo.dto.request;

import com.votehaeduo.entity.Comment;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentRequestDto {

    @NotBlank(message = "댓글 내용은 공백이 아니어야 합니다.")
    private String content;
    @NotNull(message = "댓글 등록 일자를 입력해주세요.")
    private LocalDate date;
    @NotBlank(message = "작성자는 공백이 아니어야 합니다.")
    private String createdBy;
    @NotNull(message = "사용자 id는 필수 입니다.")
    private Long memberId;

    public Comment toEntity() {
        return Comment.builder()
                .content(this.content)
                .date(this.date)
                .createdBy(this.createdBy)
                .memberId(this.memberId)
                .build();
    }

}
