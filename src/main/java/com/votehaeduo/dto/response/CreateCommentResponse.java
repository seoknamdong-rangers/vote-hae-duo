package com.votehaeduo.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.votehaeduo.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentResponse {

    private Long id;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate date;
    private String nickname;
    private Long memberId;

    public static CreateCommentResponse from(Comment comment) {
        return new CreateCommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getDate(),
                comment.getNickname(),
                comment.getMemberId()
        );
    }

}
