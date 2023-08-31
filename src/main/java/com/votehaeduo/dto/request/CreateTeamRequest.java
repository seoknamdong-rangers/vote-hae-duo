package com.votehaeduo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTeamRequest {

    @NotNull(message = "팀 매칭을 할 유저들이 빈칸입니다.")
    private Set<Long> memberIds;

    @NotNull(message = "몇개의 팀을 만들지 정해주세요.")
    private Long teamCount;

    @NotNull(message = "팀 매칭 리더가 빈칸입니다.")
    private Long createdMemberId;

}
