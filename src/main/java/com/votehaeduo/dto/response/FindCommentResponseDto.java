package com.votehaeduo.dto.response;

import com.votehaeduo.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindCommentResponseDto {

    private Long id;
    private String content;
    private String createdBy;
    private LocalDate date;

    public static FindCommentResponseDto from(Comment comment) {
        return new FindCommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedBy(),
                comment.getDate()
        );
    }

}
