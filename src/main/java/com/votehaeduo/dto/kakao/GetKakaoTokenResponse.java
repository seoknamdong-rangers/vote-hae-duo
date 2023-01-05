package com.votehaeduo.dto.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetKakaoTokenResponse {

    private static final int TOKEN_BODY_INDEX = 1;

    @JsonProperty("id_token")
    private String idToken;

    public String getIdTokenBody() {
        return idToken.split("\\.")[TOKEN_BODY_INDEX];
    }

}
