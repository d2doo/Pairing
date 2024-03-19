package com.ssafy.i10a709be.domain.member.service;

import com.ssafy.i10a709be.domain.member.dto.MemberLoginResDto;
import com.ssafy.i10a709be.domain.member.dto.MemberTokenDto;

public interface MemberService {
    MemberTokenDto login(MemberLoginResDto memberLoginResDto);

    boolean removeMember(String memberId);

    void logout(String memberId);
}
