package com.ssafy.i10a709be.domain.member.oauth;

import com.ssafy.i10a709be.domain.member.entity.Member;
import com.ssafy.i10a709be.domain.member.enums.OAuthProvider;
import com.ssafy.i10a709be.domain.member.exception.TokenInvalidException;
import com.ssafy.i10a709be.domain.member.exception.TokenMissingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Component
@Slf4j
public class GoogleOAuthClient implements OAuthClient {
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;
    @Value("${spring.security.oauth2.client.registration.google.authorization-grant-type}")
    private String authorizationGrantType;
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.provider.google.token-uri}")
    private String tokenUri;
    @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
    private String userInfoUri;

    @Override
    public String getAccessToken(String code) {
        WebClient webClient = WebClient.create();
        String decodedCode = URLDecoder.decode(code, StandardCharsets.UTF_8);

        String accessToken = "";

        try {
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            //StringBuilder sb = new StringBuilder();
            body.add("client_id", clientId);
            body.add("client_secret", clientSecret);
            body.add("grant_type", authorizationGrantType);
            body.add("redirect_uri", redirectUri);
            body.add("code", decodedCode);

            log.info(body.toString());
            Map<String, String> response = webClient.post()
                    .uri(tokenUri)
                    .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
                    .body(BodyInserters.fromFormData(body))
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            log.info(response.toString());
            accessToken = response.get("access_token");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new TokenMissingException();
        }

        return accessToken;
    }

    @Override
    public Member getMemberInfo(String token) {
        WebClient webClient = WebClient.create();

        try {
            Map<String, Object> response = webClient.get()
                    .uri(userInfoUri)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response != null) {
                String nickname = (String) response.get("name");
                String email = (String) response.get("email");
                String profileImageUrl = (String) response.get("picture");

                return Member.builder()
                        .email(email)
                        .nickname(nickname)
                        .provider(OAuthProvider.GOOGLE)
                        .profileImage(profileImageUrl)
                        .build();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new TokenInvalidException();
        }

        return null;
    }
}
