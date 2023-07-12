package com.votehaeduo.dto;

import com.votehaeduo.entity.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CreateTeamPayloadTest {

    @Test
    @DisplayName("")
    void from() {
        // given
        Team team = Team.builder()
                .id(1L)
                .teamName("1 팀")
                .memberNicknames(Set.of("성준", "성욱"))
                .createdDate(LocalDate.now())
                .createdMemberId(1L)
                .voteId(1L)
                .build();
        CreateTeamPayload expected = CreateTeamPayload.builder()
                .id(1L)
                .teamName("1 팀")
                .memberNickname(Set.of("성준", "성욱"))
                .createdDate(LocalDate.now())
                .createdMemberId(1L)
                .voteId(1L)
                .build();

        // when
        CreateTeamPayload result = CreateTeamPayload.from(team);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }

}