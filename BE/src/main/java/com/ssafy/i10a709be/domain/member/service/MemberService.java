package com.ssafy.i10a709be.domain.member.service;

import com.ssafy.i10a709be.domain.member.dto.MemberLoginResDto;

public interface MemberService {
    MemberLoginResDto login(MemberLoginResDto memberLoginResDto);

    boolean removeMember(String memberId);
}
