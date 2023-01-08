package com.votehaeduo.config;

import com.votehaeduo.client.KakaoApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApiClientConfig {

    @Bean
    public KakaoApiClient kakaoApiClient(WebClient.Builder webClientBuilder, @Value("${kakao.api.endpoint}") String kakaoApiEndpoint) {
        return new KakaoApiClient(webClientBuilder.baseUrl(kakaoApiEndpoint).build());
    }

}
