package com.votehaeduo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.votehaeduo.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamPayload {

    private Long id;
    private Set<String> teamMembers;
    private Long createdMemberId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdDateTime;
    private Long voteId;

    public static TeamPayload from(Team team) {
        return new TeamPayload(
                team.getId(),
                team.getTeamMembers(),
                team.getCreatedMemberId(),
                team.getCreatedDateTime(),
                team.getVoteId()
        );
    }

}
