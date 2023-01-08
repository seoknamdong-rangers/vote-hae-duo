package com.votehaeduo.dto.request;

import com.votehaeduo.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Builder
@RequiredArgsConstructor
public class CreateMemberRequest {

    @NotBlank
    private final String kakaoMemberNo;

    @NotBlank
    private final String nickname;

    @Email
    private final String email;

    public Member toEntity() {
        return Member.builder()
                .kakaoMemberNo(kakaoMemberNo)
                .nickname(nickname)
                .email(email)
                .build();
    }

}
