package com.votehaeduo.dto.response;

import com.votehaeduo.dto.TeamPayload;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FindAllTeamByVoteResponse {

    private List<TeamPayload> teams;

}
