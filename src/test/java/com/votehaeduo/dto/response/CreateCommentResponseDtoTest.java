package com.votehaeduo.dto.response;

import com.votehaeduo.entity.Comment;
import com.votehaeduo.entity.Vote;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class CreateCommentResponseDtoTest {

    @Test
    @DisplayName("CreateCommentResponseDto of Test")
    void of() {
        // given
        Long id = new Random().nextLong();
        Vote vote = Vote.builder()
                .id(id)
                .comments(List.of(Comment.builder()
                        .id(id)
                        .content("정말 재밌다..")
                        .date(LocalDate.of(2023, 5, 23))
                        .createdBy("성준")
                        .memberId(1L)
                        .build()))
                .build();
        CreateCommentResponseDto expected = new CreateCommentResponseDto(
                id, "정말 재밌다..", LocalDate.of(2023, 5, 23),
                "성준", 1L);

        // when
        CreateCommentResponseDto result = CreateCommentResponseDto.of(vote, id);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

}