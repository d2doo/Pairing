package com.ssafy.i10a709be.domain.member.oauth;

public interface OAuthClient {
    String getAccessToken(String code);
    String getMemberInfo(String token);
}
