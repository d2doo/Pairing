package com.ssafy.i10a709be.domain.member.oauth;

import com.ssafy.i10a709be.domain.member.exception.TokenInvalidException;
import com.ssafy.i10a709be.domain.member.exception.TokenMissingException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class KakaoOAuthClient implements OAuthClient{
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
            throw new TokenMissingException();
        }

        return accessToken;
    }

    @Override
    public String getMemberInfo(String accessToken) {
        WebClient webClient = WebClient.create();

        try {
            Map<String, Object> response = webClient.get()
                    .uri(userInfoUri)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response != null) {
                Map<String, Object> properties = (Map<String, Object>) response.get("properties");
                if (properties != null) {
                    return (String) properties.get("nickname");
                }
            }
        } catch (Exception e) {
            throw new TokenInvalidException();
        }

        return null;
    }
}
