package com.votehaeduo.service;

import com.votehaeduo.dto.CreateTeamPayload;
import com.votehaeduo.dto.MemberPayload;
import com.votehaeduo.dto.request.CreateTeamRequest;
import com.votehaeduo.entity.Team;
import com.votehaeduo.repository.TeamRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @InjectMocks
    private TeamService teamService;

    @Mock
    private TeamRepository teamRepository;

    @Test
    @DisplayName("팀 매칭")
    void createRandomTeam() {
        // given
        List<Team> teams = List.of(
                Team.builder()
                        .teamName("1 팀")
                        .createdDate(LocalDate.now())
                        .memberNicknames(Set.of("성준", "성욱"))
                        .createdMemberId(1L)
                        .voteId(1L)
                        .build(),
                Team.builder()
                        .teamName("2 팀")
                        .createdDate(LocalDate.now())
                        .memberNicknames(Set.of("준성", "영수"))
                        .createdMemberId(1L)
                        .voteId(1L)
                        .build()
        );
        given(teamRepository.saveAll(any())).willReturn(teams);

        List<CreateTeamPayload> expectedResult = List.of(
                CreateTeamPayload.builder()
                        .teamName("1 팀")
                        .createdDate(LocalDate.now())
                        .memberNickname(Set.of("성준", "성욱"))
                        .createdMemberId(1L)
                        .voteId(1L)
                        .build(),
                CreateTeamPayload.builder()
                        .teamName("2 팀")
                        .createdDate(LocalDate.now())
                        .memberNickname(Set.of("준성", "영수"))
                        .createdMemberId(1L)
                        .voteId(1L)
                        .build()
        );

        // when
        List<MemberPayload> memberPayloads = new ArrayList<>(List.of(
                MemberPayload.builder().id(1L).nickname("성준").build(),
                MemberPayload.builder().id(2L).nickname("성욱").build(),
                MemberPayload.builder().id(3L).nickname("준성").build(),
                MemberPayload.builder().id(4L).nickname("영수").build()
        ));
        CreateTeamRequest createTeamRequest = CreateTeamRequest.builder()
                .memberIds(Set.of(1L, 2L, 3L, 4L))
                .teamCount(2L)
                .createdMemberId(1L)
                .build();
        List<CreateTeamPayload> createTeamPayloads = teamService.createRandomTeam(1L, memberPayloads, createTeamRequest);

        // then
        Assertions.assertThat(createTeamPayloads).usingRecursiveComparison().isEqualTo(expectedResult);
    }

}