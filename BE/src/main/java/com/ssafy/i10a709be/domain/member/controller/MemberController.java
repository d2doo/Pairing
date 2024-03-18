package com.ssafy.i10a709be.domain.member.controller;

import com.ssafy.i10a709be.domain.member.dto.MemberLoginResDto;
import com.ssafy.i10a709be.domain.member.oauth.OAuthClient;
import com.ssafy.i10a709be.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final OAuthClient kakaoOAuthClient;

    @PostMapping("/login/kakao/{provider}")
    public ResponseEntity<MemberLoginResDto> kakaoLogin(@PathVariable String provider){
        String accessToken = kakaoOAuthClient.getAccessToken(provider);
        MemberLoginResDto memberLoginResDto = kakaoOAuthClient.getMemberInfo(accessToken);

        memberService.login(memberLoginResDto);

        return ResponseEntity.status(HttpStatus.OK).body(memberLoginResDto);
    }
}
