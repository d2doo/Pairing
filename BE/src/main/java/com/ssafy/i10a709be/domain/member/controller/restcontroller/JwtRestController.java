package com.ssafy.i10a709be.domain.member.controller.restcontroller;

import com.ssafy.i10a709be.common.security.jwt.JwtValidator;
import com.ssafy.i10a709be.domain.member.exception.TokenInvalidException;
import com.ssafy.i10a709be.domain.member.exception.TokenMissingException;
import com.ssafy.i10a709be.domain.member.service.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class JwtRestController {
    private final JwtService jwtService;
    private final JwtValidator jwtValidator;

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> silentRefresh(@CookieValue(name = "Authorization", required = false) Cookie refresh){
        log.info("refresh token: {}", refresh);
        String refreshToken = refresh.getValue();
        if (null != refreshToken){
            String memberId = jwtValidator.isValidToken(refreshToken);
            if (null != memberId){
                List<String> tokens = jwtService.refresh(memberId);

                ResponseCookie responseCookie = ResponseCookie.from("Authorization", tokens.get(1))
                        .httpOnly(true)
                        .secure(true)
                        .sameSite("None")
                        .maxAge(60 * 60 * 24 * 7)
                        .path("/")
                        .build();

                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.SET_COOKIE, responseCookie.toString());

                Map<String, String> map = new HashMap<>();
                map.put("accessToken", "Bearer " + tokens.get(0));

                return ResponseEntity.status(HttpStatus.OK).headers(headers).body(map);
            } else{
                throw new TokenInvalidException();
            }
        } else{
            throw new TokenMissingException();
        }
    }
}
