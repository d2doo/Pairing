package com.ssafy.i10a709be.domain.member.service;

import com.ssafy.i10a709be.common.security.jwt.JwtProvider;
import com.ssafy.i10a709be.domain.member.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService{
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Override
    public List<String> refresh(String memberId) {
        List<String> tokens = jwtProvider.generateToken(memberId);

        memberRepository.findById(memberId).ifPresent(member ->{
            member.updateRefreshToken(tokens.get(1));
        });

        return tokens;
    }
}
