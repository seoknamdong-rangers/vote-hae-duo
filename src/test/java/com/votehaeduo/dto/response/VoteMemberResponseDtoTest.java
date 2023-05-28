package com.votehaeduo.dto.response;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class VoteMemberResponseDtoTest {

    @Test
    void from() {
        // given
        VoteMemberResponseDto expected = new VoteMemberResponseDto(Set.of(1L, 2L),2L);

        // when
        VoteMemberResponseDto result = VoteMemberResponseDto.from(Set.of(1L, 2L));

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

}