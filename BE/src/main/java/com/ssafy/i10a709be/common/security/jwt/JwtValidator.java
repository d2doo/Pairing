package com.ssafy.i10a709be.common.security.jwt;

import com.ssafy.i10a709be.common.exception.TokenInvalidException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

    @Value("${spring.security.jwt.secret-key}")
    public String JWT_SECRETKEY;


    public String isValidAccessToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(JWT_SECRETKEY.getBytes(StandardCharsets.UTF_8));
        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            /*
                토큰이 만료되지 않았다면, 토큰 내 Member id 반환
                토큰이 만료되었다면, null 반환
             */
            if (!claims.getPayload().getExpiration().before(new Date())) {
                return extractMemberId(claims);
            } else{
                return null;
            }
        } catch (JwtException e) {
            throw new TokenInvalidException();
        }
    }

    /*
        인증된 토큰의 payload에서 userId를 추출해내는 Method
     */
    private String extractMemberId(Jws<Claims> claims) {
        return claims.getPayload().get("uuid", String.class);
    }
}
