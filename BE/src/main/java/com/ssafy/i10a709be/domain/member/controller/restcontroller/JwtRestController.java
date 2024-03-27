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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JwtRestController {
    private final JwtService jwtService;
    private final JwtValidator jwtValidator;

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> silentRefresh(HttpServletRequest request, HttpServletResponse response){
        String refreshToken = null;
        Cookie refreshTokenCookie = null;
        String header = "Authorization";

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (header.equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    refreshTokenCookie = cookie;
                    break;
                }
            }
        }

        if (null != refreshToken){
            String memberId = jwtValidator.isValidToken(refreshToken);
            if (null != memberId){
                List<String> tokens = jwtService.refresh(memberId);

                refreshTokenCookie.setValue(tokens.get(1));
                refreshTokenCookie.setMaxAge(1000 * 60 * 60 * 24 * 7);
                response.addCookie(refreshTokenCookie);

                Map<String, String> map = new HashMap<>();
                map.put("accessToken", "Bearer " + tokens.get(0));

                return ResponseEntity.status(HttpStatus.OK).body(map);
            } else{
                throw new TokenInvalidException();
            }
        } else{
            throw new TokenMissingException();
        }
    }
}
