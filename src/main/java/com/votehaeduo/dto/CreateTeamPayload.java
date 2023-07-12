package com.votehaeduo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.votehaeduo.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTeamPayload {

    private Long id;
    private String teamName;
    private Set<String> memberNickname;
    private Long createdMemberId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate createdDate;
    private Long voteId;

    public static CreateTeamPayload from(Team team) {
        return new CreateTeamPayload(
                team.getId(),
                team.getTeamName(),
                team.getMemberNicknames(),
                team.getCreatedMemberId(),
                team.getCreatedDate(),
                team.getVoteId()
        );
    }

}
