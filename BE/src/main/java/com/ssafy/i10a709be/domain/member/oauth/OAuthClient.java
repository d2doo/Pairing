package com.ssafy.i10a709be.domain.member.oauth;

import com.ssafy.i10a709be.domain.member.dto.MemberSummaryResponseDto;
import com.ssafy.i10a709be.domain.member.entity.Member;

public interface OAuthClient {
    String getAccessToken(String code);
    Member getMemberInfo(String token);
}
