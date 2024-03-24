package com.ssafy.i10a709be.domain.member.service;

import com.ssafy.i10a709be.domain.member.dto.MemberUpdateRequestDto;
import com.ssafy.i10a709be.domain.member.entity.Member;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface MemberService {
    List<String> login(Member member);

    boolean removeMember(String memberId);

    Member findMemberById(String memberId);

    Member findMemberByEmail( String email );

    @Transactional
    Member updateMemberDetails(String memberId, MemberUpdateRequestDto memberUpdateRequestDto);

    void logout(String memberId);
}
