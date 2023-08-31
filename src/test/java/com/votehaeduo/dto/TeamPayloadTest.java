package com.votehaeduo.dto;

import com.votehaeduo.entity.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class TeamPayloadTest {

    @Test
    @DisplayName("TeamPayload from 테스트")
    void from() {
        // given
        LocalDateTime localDateTimeTest = LocalDateTime.now();
        Team team = Team.builder()
                .id(1L)
                .teamMembers(Set.of("성준", "성욱"))
                .createdDateTime(localDateTimeTest)
                .createdMemberId(1L)
                .voteId(1L)
                .build();
        TeamPayload expected = TeamPayload.builder()
                .id(1L)
                .teamMembers(Set.of("성준", "성욱"))
                .createdDateTime(localDateTimeTest)
                .createdMemberId(1L)
                .voteId(1L)
                .build();

        // when
        TeamPayload result = TeamPayload.from(team);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

}