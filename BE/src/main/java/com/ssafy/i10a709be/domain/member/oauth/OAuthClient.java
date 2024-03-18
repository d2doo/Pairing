package com.ssafy.i10a709be.domain.member.oauth;

import com.ssafy.i10a709be.domain.member.dto.MemberLoginResDto;

public interface OAuthClient {
    String getAccessToken(String code);
    MemberLoginResDto getMemberInfo(String token);
}
