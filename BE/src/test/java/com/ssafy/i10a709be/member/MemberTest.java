package com.ssafy.i10a709be.member;


import com.ssafy.i10a709be.domain.member.dto.MemberSummaryResponseDto;
import com.ssafy.i10a709be.domain.member.entity.Member;
import com.ssafy.i10a709be.domain.member.oauth.OAuthClient;
import com.ssafy.i10a709be.domain.member.repository.MemberRepository;
import com.ssafy.i10a709be.domain.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MemberTest {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OAuthClient kakaoOAuthClient;

    private static final String email = "cqqudgjs@naver.com";

    /*
    https://kauth.kakao.com/oauth/authorize?client_id=5a1bf993c63b329bf6aeb64d1d0de64b&redirect_uri=https://j10a709.p.ssafy.io/auth/kakao&response_type=code
    매 Test마다 변경 필요
     */
//    private static final String kakaoProvider = "uC1cdpbM6tyP4HoIipwuoxJ-BQ-kLELN8MXxUd2zHniSqs2bRn2VdaOwM3MKKw0fAAABjk0Z5h1V7imzm104lw";
//
//    @Test
//    @Transactional
//    void login(){
//        String accessToken = kakaoOAuthClient.getAccessToken(kakaoProvider);
//        MemberSummaryResponseDto memberSummaryResponseDto = kakaoOAuthClient.getMemberInfo(accessToken);
//
//        memberService.login(memberSummaryResponseDto);
//        Optional<Member> member = memberRepository.findByEmail(memberSummaryResponseDto.getEmail());
//        member.ifPresent(value -> Assertions.assertThat(memberSummaryResponseDto.getNickname()).isEqualTo(value.getNickname()));
//    }

    @Test
    @Transactional
    @DisplayName("멤버 삭제")
    void removeMember(){
        memberRepository.findByEmail(email).ifPresent(member -> {
            String memberId = member.getMemberId();
            memberService.removeMember(memberId);
        });

        memberRepository.findByEmail(email).ifPresent(member ->{
            Assertions.assertThat(member.getIsDeleted()).isEqualTo(true);
        });
    }

    @Test
    @Transactional
    void logout(){
        memberRepository.findByEmail(email).ifPresent(member ->{
            String memberId = member.getMemberId();
            memberService.logout(memberId);
        });
    }
}