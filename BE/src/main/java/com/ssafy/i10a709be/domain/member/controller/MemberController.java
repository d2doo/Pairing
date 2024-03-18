package com.ssafy.i10a709be.domain.member.controller;

import com.ssafy.i10a709be.common.security.jwt.JwtProvider;
import com.ssafy.i10a709be.domain.member.dto.MemberLoginResDto;
import com.ssafy.i10a709be.domain.member.oauth.OAuthClient;
import com.ssafy.i10a709be.domain.member.service.MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final JwtProvider jwtProvider;
    private final OAuthClient kakaoOAuthClient;

    @PostMapping("/login/kakao/{provider}")
    public ResponseEntity<MemberLoginResDto> kakaoLogin(@PathVariable String provider, HttpServletResponse response){
        String accessToken = kakaoOAuthClient.getAccessToken(provider);
        MemberLoginResDto memberLoginResDto = kakaoOAuthClient.getMemberInfo(accessToken);

        memberService.login(memberLoginResDto);
        accessToken = jwtProvider.generateToken(response, memberLoginResDto.getMemberId());  // Client 측에 보내기 위해 새로 만든 토큰
        response.addHeader("Authorization", "Bearer " + accessToken);

        return ResponseEntity.status(HttpStatus.OK).body(memberLoginResDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> memberRemove(){
        String memberId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (memberService.removeMember(memberId)){
            return ResponseEntity.status(HttpStatus.OK).build();
        } else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
