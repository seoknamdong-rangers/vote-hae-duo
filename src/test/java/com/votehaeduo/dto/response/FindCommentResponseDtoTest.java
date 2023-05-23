package com.votehaeduo.dto.response;

import com.votehaeduo.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class FindCommentResponseDtoTest {

    @Test
    @DisplayName("FindCommentResponseDto from Test")
    void from() {
        // given
        Comment comment = Comment.builder()
                .id(1L)
                .content("정말 재밌다..")
                .date(LocalDate.of(2023, 5, 23))
                .createdBy("성준")
                .build();
        FindCommentResponseDto expected = new FindCommentResponseDto(
                1L, "정말 재밌다..", "성준",
                LocalDate.of(2023, 5, 23));

        // when
        FindCommentResponseDto result = FindCommentResponseDto.from(comment);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

}