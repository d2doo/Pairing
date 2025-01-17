package com.ssafy.i10a709be.common.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {
    @Value("${spring.security.jwt.secret-key}")
    public String JWT_SECRETKEY;

    public List<String> generateToken(String uuid){
        String accessToken = createAccessToken(uuid);
        String refreshToken = createRefreshToken(uuid);

        List<String> tokens = new ArrayList<>();
        tokens.add(accessToken);
        tokens.add(refreshToken);

        return tokens;
    }

    private String createAccessToken(String uuid) {
        Date date = new Date();
        SecretKey key = Keys.hmacShaKeyFor(JWT_SECRETKEY.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .issuer("Exodia")
                .claim("uuid", uuid)
                .issuedAt(date)
                .expiration(new Date(date.getTime() + (1000 * 60 * 30)))  // 30분 설정
                .signWith(key).compact();
    }

    private String createRefreshToken(String uuid){
        Date date = new Date();
        SecretKey key = Keys.hmacShaKeyFor(JWT_SECRETKEY.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .issuer("Exodia")
                .claim("uuid", uuid)
                .issuedAt(date)
                .expiration(new Date(date.getTime() + (1000 * 60 * 60 * 24 * 7)))  // 7일 설정
                .signWith(key).compact();
    }
}
