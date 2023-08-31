package com.votehaeduo.service;

import com.votehaeduo.dto.MemberPayload;
import com.votehaeduo.dto.TeamPayload;
import com.votehaeduo.dto.request.CreateTeamRequest;
import com.votehaeduo.entity.Team;
import com.votehaeduo.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    // 원하는 팀 수 만큼 인원 나누기
    @Transactional
    public TeamPayload createRandomTeam(Long voteId, List<MemberPayload> memberPayloads,
                                        CreateTeamRequest createTeamRequest) {
        Collections.shuffle(memberPayloads);
        int membersPerTeam = (int) (memberPayloads.size() / createTeamRequest.getTeamCount());
        int remainingMembers = (int) (memberPayloads.size() % createTeamRequest.getTeamCount());

        AtomicInteger startIndex = new AtomicInteger(0);
        Set<String> teamMembers = IntStream.range(0, Math.toIntExact(createTeamRequest.getTeamCount()))
                .mapToObj(i -> {
                    int teamSize = membersPerTeam + (i < remainingMembers ? 1 : 0);
                    List<String> teamMemberNicknames = memberPayloads.stream()
                            .map(MemberPayload::getNickname)
                            .skip(startIndex.getAndAdd(teamSize))
                            .limit(teamSize)
                            .collect(Collectors.toList());
                    return String.join(", ", teamMemberNicknames);
                })
                .collect(Collectors.toSet());
        Team team = Team.of(createTeamRequest.getCreatedMemberId(), voteId, teamMembers);

        return TeamPayload.from(teamRepository.save(team));
    }

    @Transactional
    public List<TeamPayload> findAllTeamByVoteId(Long voteId) {
        return teamRepository.findTeamsByVoteIdWithBatchSize(voteId).stream()
                .map(TeamPayload::from)
                .collect(Collectors.toList());
    }

}
