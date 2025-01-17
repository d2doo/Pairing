package com.ssafy.i10a709be.domain.member.oauth;

import com.ssafy.i10a709be.domain.member.dto.MemberSummaryResponseDto;
import com.ssafy.i10a709be.domain.member.entity.Member;
import com.ssafy.i10a709be.domain.member.enums.OAuthProvider;
import com.ssafy.i10a709be.domain.member.exception.TokenInvalidException;
import com.ssafy.i10a709be.domain.member.exception.TokenMissingException;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
public class KakaoOAuthClient implements OAuthClient {
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;
    @Value("${spring.security.oauth2.client.registration.kakao.authorization-grant-type}")
    private String authorizationCode;
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String secretCode;

    @Value("${spring.security.oauth2.client.provider.kakao.token_uri}")
    private String tokenUri;
    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String userInfoUri;

    @Override
    public String getAccessToken(String code) {
        WebClient webClient = WebClient.create();

        String accessToken = "";

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=" + authorizationCode)
                    .append("&client_id=" + clientId)
                    .append("&redirect_uri=" + redirectUri)
                    .append("&code=" + code)
                    .append("&client_secret=" + secretCode);

            Map<String, String> response = webClient.post()
                    .uri(tokenUri)
                    .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
                    .body(BodyInserters.fromValue(sb.toString()))
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            accessToken = response.get("access_token");
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new TokenMissingException();
        }

        return accessToken;
    }

    @Override
    public Member getMemberInfo(String accessToken) {
        WebClient webClient = WebClient.create();

        try {
            Map<String, Object> response = webClient.get()
                    .uri(userInfoUri)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response != null) {
                Map<String, Object> kakaoAccount = (Map<String, Object>) response.get("kakao_account");
                Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
                String nickname = (String) profile.get("nickname");
                String email = (String) kakaoAccount.get("email");
                String profileImageUrl = (String) profile.get("profile_image_url");

                return Member.builder()
                        .email(email)
                        .nickname(nickname)
                        .provider(OAuthProvider.KAKAO)
                        .profileImage(profileImageUrl)
                        .build();
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new TokenInvalidException();
        }

        return null;
    }
}
