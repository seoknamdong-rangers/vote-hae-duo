package com.votehaeduo.dto.kakao;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GetKakaoTokenRequest {

    @Builder.Default
    private final String grantType = "authorization_code";
    private final String code;
    private final String clientId;
    private final String redirectUrl;

}
