package com.ssafy.i10a709be.domain.member.service;

import com.ssafy.i10a709be.domain.member.dto.MemberLoginResDto;
import com.ssafy.i10a709be.domain.member.entity.Member;
import com.ssafy.i10a709be.domain.member.enums.OAuthProvider;
import com.ssafy.i10a709be.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

    @Override
    public int login(MemberLoginResDto memberLoginResDto) {
        Optional<Member> member = memberRepository.findByEmail(memberLoginResDto.getEmail());
        if (member.isEmpty()){
            memberRepository.save(Member.builder()
                            .email(memberLoginResDto.getEmail())
                            .nickname(memberLoginResDto.getNickname())
                            .profileImage(memberLoginResDto.getProfileImage())
                            .provider(OAuthProvider.KAKAO)
                    .build());
        }

        return 1;
    }
}
