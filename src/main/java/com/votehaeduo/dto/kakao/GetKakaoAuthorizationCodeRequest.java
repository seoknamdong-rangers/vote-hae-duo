package com.votehaeduo.dto.kakao;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GetKakaoAuthorizationCodeRequest {

    private final String prompt;

}
