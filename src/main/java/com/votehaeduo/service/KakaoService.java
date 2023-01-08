package com.votehaeduo.service;

import com.votehaeduo.client.KakaoApiClient;
import com.votehaeduo.dto.kakao.GetKakaoAuthorizationCodeRequest;
import com.votehaeduo.dto.kakao.GetKakaoTokenRequest;
import com.votehaeduo.dto.kakao.GetKakaoTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class KakaoService {

    @Value("${kakao.api.endpoint}")
    private String kakaoApiEndpoint;
    @Value("${kakao.api.client-id}")
    private String kakaoApiClientId;
    @Value("${kakao.api.redirect-url}")
    private String redirectUrl;

    private final KakaoApiClient kakaoApiClient;

    public URI getAuthorizationCodeUrl(GetKakaoAuthorizationCodeRequest request) {
        UriBuilder uriBuilder = new DefaultUriBuilderFactory(kakaoApiEndpoint)
                .uriString("/oauth/authorize")
                .queryParam("client_id", kakaoApiClientId)
                .queryParam("redirect_uri", redirectUrl)
                .queryParam("response_type", "code");

        if (request.getPrompt() != null) {
            uriBuilder.queryParam("prompt", request.getPrompt());
        }
        return uriBuilder.build();
    }

    public Mono<String> getOauthIdTokenBody(GetKakaoTokenRequest request) {
        GetKakaoTokenRequest tokenRequest = GetKakaoTokenRequest.builder()
                .code(request.getCode())
                .clientId(kakaoApiClientId)
                .redirectUrl(redirectUrl)
                .build();

        return kakaoApiClient.getOauthToken(tokenRequest)
                .map(GetKakaoTokenResponse::getIdTokenBody);
    }
}
