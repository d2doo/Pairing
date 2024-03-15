package com.ssafy.i10a709be.common.security.filter;

import com.ssafy.i10a709be.common.security.jwt.JwtProvider;
import com.ssafy.i10a709be.common.security.jwt.JwtValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import javax.security.sasl.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtValidator jwtValidator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader("Authorization");

        // access token이 유효한 경우만 Authentication 객체 생성
        if (null != accessToken) {
            String uuid = jwtValidator.isValidAccessToken(accessToken);
            if (null != uuid) {
                GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(uuid, null, Collections.singletonList(authority)));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else{
                throw new AuthenticationException();
            }
        }

        filterChain.doFilter(request, response);
    }
}
