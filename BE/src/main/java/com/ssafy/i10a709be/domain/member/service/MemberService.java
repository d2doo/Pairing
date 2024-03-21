package com.ssafy.i10a709be.domain.member.service;

import com.ssafy.i10a709be.domain.member.dto.MemberLoginResDto;
import com.ssafy.i10a709be.domain.member.dto.MemberTokenDto;
import com.ssafy.i10a709be.domain.member.dto.MemberUpdateRequestDto;
import com.ssafy.i10a709be.domain.member.entity.Member;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MemberService {
    MemberTokenDto login(MemberLoginResDto memberLoginResDto);

    boolean removeMember(String memberId);

    Member findMemberById(String memberId);

    Member findMemberByEmail( String email );

    @Transactional
    Member updateMemberDetails(String memberId, MemberUpdateRequestDto memberUpdateRequestDto);

    void logout(String memberId);
}
