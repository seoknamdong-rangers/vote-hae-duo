package com.votehaeduo.dto;

import com.votehaeduo.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberPayload {

    private Long id;
    private String kakaoMemberNo;
    private String nickname;
    private String email;
    private String profileUrl;

    public static MemberPayload from(Member member) {
        return MemberPayload.builder()
                .id(member.getId())
                .kakaoMemberNo(member.getKakaoMemberNo())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .profileUrl(member.getProfileUrl())
                .build();
    }

}
