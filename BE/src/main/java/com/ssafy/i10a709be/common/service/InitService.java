package com.ssafy.i10a709be.common.service;


import com.ssafy.i10a709be.domain.member.entity.Member;
import com.ssafy.i10a709be.domain.member.enums.OAuthProvider;
import com.ssafy.i10a709be.domain.member.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InitService {

    private final MemberRepository memberRepository;

    @PostConstruct
    @Transactional
    public void insertInit(){
        Member member = Member.builder()
                .email("cqqudgjs@naver.com")
                .nickname("라이빵허")
                .provider(OAuthProvider.KAKAO)
                .build();
        member.updateRefreshToken("eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJFeG9kaWEiLCJ1dWlkIjoiNDhiZWY2MzYtZGZkNy00YmM3LTk4ZjUtNTIzMmM0MjZlYzAxIiwiaWF0IjoxNzEwODA2NTIwLCJleHAiOjE3MTE0MTEzMjB9.x67DUg4brARUkfCFT66t89lZqooj0cmWWxd0Lj9glpM");
        memberRepository.save( member );
        Member findMember = memberRepository.findByEmail("cqqudgjs@naver.com").get();
        log.info( findMember.getEmail() + " 테스트 데이터 등록 완료 id: " + member.getMemberId() + " ClassName: "+ this.getClass().getName() );
    }
}
