package com.ssafy.i10a709be.domain.member.service;

import com.ssafy.i10a709be.domain.member.dto.MemberLoginResDto;
import com.ssafy.i10a709be.domain.member.dto.MemberTokenDto;
import com.ssafy.i10a709be.domain.member.entity.Member;
import java.util.Optional;

public interface MemberService {
    MemberTokenDto login(MemberLoginResDto memberLoginResDto);

    boolean removeMember(String memberId);

    Optional<Member> findMemberById(String memberId);

    Member findMemberByEmail( String email );

    void logout(String memberId);
}
