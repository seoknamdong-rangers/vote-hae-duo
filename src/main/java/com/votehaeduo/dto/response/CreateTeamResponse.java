package com.votehaeduo.dto.response;

import com.votehaeduo.dto.CreateTeamPayload;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateTeamResponse {

    private List<CreateTeamPayload> createTeamPayloads;

}
