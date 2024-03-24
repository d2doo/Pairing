package com.ssafy.i10a709be.domain.member.controller.restcontroller;

import com.ssafy.i10a709be.common.security.jwt.JwtProvider;
import com.ssafy.i10a709be.domain.member.dto.*;
import com.ssafy.i10a709be.domain.member.entity.Member;
import com.ssafy.i10a709be.domain.member.oauth.OAuthClient;
import com.ssafy.i10a709be.domain.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberRestController {
    private final MemberService memberService;
    private final OAuthClient kakaoOAuthClient;

    @GetMapping
    public ResponseEntity<MemberResponseDto> findMember(@AuthenticationPrincipal String memberId) {
        return ResponseEntity.ok(MemberResponseDto.fromEntity(memberService.findMemberById(memberId)));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberSummaryResponseDto> findMemberById(@PathVariable String memberId) {
        return ResponseEntity.ok(MemberSummaryResponseDto.fromEntity(memberService.findMemberById(memberId)));
    }

    @PatchMapping
    public ResponseEntity<MemberResponseDto> updateMemberDetails(@AuthenticationPrincipal String memberId, @RequestBody MemberUpdateRequestDto memberUpdateRequestDto) {
        return ResponseEntity.ok(MemberResponseDto.fromEntity(memberService.updateMemberDetails(memberId, memberUpdateRequestDto)));
    }

    @PostMapping("/login/kakao")
    public ResponseEntity<Map<String, Object>> kakaoLogin(@RequestParam String code, HttpServletResponse response) {
        String accessToken = kakaoOAuthClient.getAccessToken(code);
        Member member = kakaoOAuthClient.getMemberInfo(accessToken);

        List<String> tokens = memberService.login(member);

        Cookie cookie = new Cookie("Authorization", tokens.get(1));
        cookie.setMaxAge(1000 * 60 * 60 * 24 * 7);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);

        Map<String, Object> map = new HashMap<>();
        map.put("member", MemberSummaryResponseDto.fromEntity(member));
        map.put("accessToken", "Bearer " + tokens.get(0));

        return ResponseEntity.status(HttpStatus.OK).body(map);
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

    // 채팅 테스트 로그인 서비스
    @GetMapping("/chat/test/login")
    public ResponseEntity<MemberSummaryResponseDto> memberTestLogin(){
        Member findMember = memberService.findMemberByEmail( "cqqudgjs@naver.com");

        MemberSummaryResponseDto returnDto = MemberSummaryResponseDto.builder()
                .memberId(findMember.getMemberId())
                .profileImage(null)
                .nickname(findMember.getNickname())
                .build();

        return ResponseEntity.ok( returnDto );
    }

}
