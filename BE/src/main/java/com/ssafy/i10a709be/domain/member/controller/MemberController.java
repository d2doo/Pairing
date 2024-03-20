package com.ssafy.i10a709be.domain.member.controller;

import com.ssafy.i10a709be.common.security.jwt.JwtProvider;
import com.ssafy.i10a709be.domain.member.dto.MemberLoginResDto;
import com.ssafy.i10a709be.domain.member.dto.MemberTokenDto;
import com.ssafy.i10a709be.domain.member.oauth.OAuthClient;
import com.ssafy.i10a709be.domain.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final OAuthClient kakaoOAuthClient;

    @PostMapping("/login/kakao")
    public ResponseEntity<MemberLoginResDto> kakaoLogin(@RequestParam String code, HttpServletResponse response) {
        String accessToken = kakaoOAuthClient.getAccessToken(code);
        MemberLoginResDto memberLoginResDto = kakaoOAuthClient.getMemberInfo(accessToken);

        MemberTokenDto memberTokenDto = memberService.login(memberLoginResDto);

        response.addHeader("Authorization", "Bearer " + memberTokenDto.getAccessToken());

        Cookie cookie = new Cookie("Authorization", memberTokenDto.getRefreshToken());
        cookie.setMaxAge(1000 * 60 * 60 * 24 * 7);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);

        return ResponseEntity.status(HttpStatus.OK).body(memberLoginResDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> memberRemove(@AuthenticationPrincipal String memberId) {
        if (memberService.removeMember(memberId)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal String memberId, HttpServletRequest request, HttpServletResponse response){
        memberService.logout(memberId);

        Cookie refreshTokenCookie = null;
        String header = "Authorization";

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (header.equals(cookie.getName())) {
                    refreshTokenCookie = cookie;
                    break;
                }
            }
        }

        refreshTokenCookie.setMaxAge(0);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
