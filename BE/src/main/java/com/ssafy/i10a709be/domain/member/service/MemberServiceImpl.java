package com.ssafy.i10a709be.domain.member.service;

import com.ssafy.i10a709be.domain.member.dto.MemberLoginResDto;
import com.ssafy.i10a709be.domain.member.entity.Member;
import com.ssafy.i10a709be.domain.member.enums.OAuthProvider;
import com.ssafy.i10a709be.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

    @Override
    public MemberLoginResDto login(MemberLoginResDto memberLoginResDto) {
        Optional<Member> member = memberRepository.findByEmail(memberLoginResDto.getEmail());
        if (member.isEmpty()){
            memberRepository.save(Member.builder()
                            .email(memberLoginResDto.getEmail())
                            .nickname(memberLoginResDto.getNickname())
                            .profileImage(memberLoginResDto.getProfileImage())
                            .provider(OAuthProvider.KAKAO)
                    .build());

            member = memberRepository.findByEmail(memberLoginResDto.getEmail());
        }
        memberLoginResDto.setMemberId(member.get().getMemberId());

        return memberLoginResDto;
    }

    @Override
    public boolean removeMember(String memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        if(member.isPresent()){
            memberRepository.delete(member.get());
            return true;
        }

        return false;
    }
}
