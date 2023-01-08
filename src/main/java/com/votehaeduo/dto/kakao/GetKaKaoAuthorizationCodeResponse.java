package com.votehaeduo.dto.kakao;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class GetKaKaoAuthorizationCodeResponse {

    private final String code;
    private final String error;

    public boolean error() {
        return error != null;
    }

}
