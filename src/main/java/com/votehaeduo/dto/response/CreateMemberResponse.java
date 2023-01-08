package com.votehaeduo.dto.response;

import com.votehaeduo.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class CreateMemberResponse {

    private final Long id;
    private final String kakaoMemberNo;
    private final String nickname;
    private final String email;

    public static CreateMemberResponse from(Member member) {
        return CreateMemberResponse.builder()
                .id(member.getId())
                .kakaoMemberNo(member.getKakaoMemberNo())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .build();
    }

}
