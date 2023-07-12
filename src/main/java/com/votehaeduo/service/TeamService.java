package com.votehaeduo.service;

import com.votehaeduo.dto.CreateTeamPayload;
import com.votehaeduo.dto.MemberPayload;
import com.votehaeduo.dto.request.CreateTeamRequest;
import com.votehaeduo.entity.Team;
import com.votehaeduo.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    // 원하는 팀 수 만큼 인원 나누기
    @Transactional
    public List<CreateTeamPayload> createRandomTeam(Long voteId, List<MemberPayload> memberPayloads,
                                                    CreateTeamRequest createTeamRequest) {
        Collections.shuffle(memberPayloads);

        int membersPerGroup = (int) (memberPayloads.size() / createTeamRequest.getTeamCount());
        int remainingMembers = (int) (memberPayloads.size() % createTeamRequest.getTeamCount());

        AtomicInteger startIndex = new AtomicInteger(1);
        List<Team> createdTeams = IntStream.range(0, Math.toIntExact(createTeamRequest.getTeamCount()))
                .mapToObj(i -> {
                    int groupSize = membersPerGroup + (i < remainingMembers ? 1 : 0);
                    Set<String> teamMemberNicknames = memberPayloads.stream()
                            .map(MemberPayload::getNickname)
                            .skip(startIndex.getAndAdd(groupSize) - 1)
                            .limit(groupSize)
                            .collect(Collectors.toSet());
                    return Team.of((long) i, createTeamRequest.getCreatedMemberId(), voteId, teamMemberNicknames);
                })
                .collect(Collectors.toList());
        return teamRepository.saveAll(createdTeams).stream()
                .map(CreateTeamPayload::from)
                .collect(Collectors.toList());
    }

}