package com.votehaeduo.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.votehaeduo.entity.Comment;
import com.votehaeduo.entity.Vote;
import com.votehaeduo.exception.comment.CommentNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentResponseDto {

    private Long id;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate date;
    private String createdBy;
    private Long memberId;

    public static CreateCommentResponseDto of(Vote vote, Long lastCommentId) {
        Comment createdComment = vote.getComments().stream()
                .filter(comment -> comment.getId().equals(lastCommentId))
                .findFirst()
                .orElseThrow(CommentNotFoundException::new);
        return new CreateCommentResponseDto(
                createdComment.getId(),
                createdComment.getContent(),
                createdComment.getDate(),
                createdComment.getCreatedBy(),
                createdComment.getMemberId()
        );
    }

}
