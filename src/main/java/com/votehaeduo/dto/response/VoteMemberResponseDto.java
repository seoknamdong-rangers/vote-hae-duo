package com.votehaeduo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VoteMemberResponseDto {

    private Set<Long> memberIds;
    private Long uniqueCount;

    public static VoteMemberResponseDto from(Set<Long> memberIds) {
        return new VoteMemberResponseDto(
                memberIds,
                (long) memberIds.size()
        );
    }

}
