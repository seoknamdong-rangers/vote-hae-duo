package com.votehaeduo.controller;

import com.votehaeduo.dto.kakao.GetKaKaoAuthorizationCodeResponse;
import com.votehaeduo.dto.kakao.GetKakaoAuthorizationCodeRequest;
import com.votehaeduo.dto.kakao.GetKakaoTokenRequest;
import com.votehaeduo.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class KakaoController {

    private final KakaoService kakaoService;

    @GetMapping("/authorize")
    public String getAuthorizationCode(GetKakaoAuthorizationCodeRequest request) {
        return "redirect:" + kakaoService.getAuthorizationCodeUrl(request);
    }

    @GetMapping("/redirect")
    public String processRedirectByError(GetKaKaoAuthorizationCodeResponse response) {
        if (response.error()) {
            return "redirect:oauth/authorize";
        }
        return "redirect:/oauth/token?code=" + response.getCode();
    }

    @GetMapping("/token")
    public String getOauthToken(GetKakaoTokenRequest request) {
        return "redirect:/votes?token=" + kakaoService.getOauthIdTokenBody(request).block();
    }

}
