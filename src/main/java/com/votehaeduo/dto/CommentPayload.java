package com.votehaeduo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.votehaeduo.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentPayload {

    private Long id;
    private String content;
    private String nickname;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate date;
    private Long memberId;

    public static CommentPayload from(Comment comment) {
        return new CommentPayload(
                comment.getId(),
                comment.getContent(),
                comment.getNickname(),
                comment.getDate(),
                comment.getMemberId()
        );
    }

}
