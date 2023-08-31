package com.votehaeduo.dto.request;

import com.votehaeduo.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class CreateCommentRequestTest {

    @Test
    @DisplayName("CreateCommentRequestDto toEntity Test")
    void toEntity() {
        // given
        CreateCommentRequest createCommentRequest = new CreateCommentRequest(
                "정말 재밌다..",
                LocalDate.of(2023, 5, 23),
                1L);
        Comment expected = Comment.builder()
                .content("정말 재밌다..")
                .date(LocalDate.of(2023, 5, 23))
                .memberId(1L)
                .memberId(1L)
                .build();

        // when
        Comment result = createCommentRequest.toEntity();

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

}