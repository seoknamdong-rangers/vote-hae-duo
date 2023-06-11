package com.votehaeduo.dto.response;

import com.votehaeduo.entity.Comment;
import com.votehaeduo.entity.Vote;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class CreateCommentResponseTest {

    @Test
    @DisplayName("CreateCommentResponseDto from Test")
    void from() {
        // given
        Long id = new Random().nextLong();
        Vote vote = Vote.builder()
                .id(id)
                .comments(List.of(Comment.builder()
                        .id(id)
                        .content("정말 재밌다..")
                        .date(LocalDate.of(2023, 5, 23))
                        .nickname("성준")
                        .memberId(1L)
                        .build()))
                .build();
        CreateCommentResponse expected = new CreateCommentResponse(
                id, "정말 재밌다..", LocalDate.of(2023, 5, 23),
                "성준", 1L);

        // when
        CreateCommentResponse result = CreateCommentResponse.from(vote.getLastComment());

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

}