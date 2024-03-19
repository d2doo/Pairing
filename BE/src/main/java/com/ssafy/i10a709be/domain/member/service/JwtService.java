package com.ssafy.i10a709be.domain.member.service;

import com.ssafy.i10a709be.domain.member.dto.MemberTokenDto;

public interface JwtService {
    MemberTokenDto refresh(String memberId);
}
