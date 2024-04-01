package com.ssafy.i10a709be.domain.member.service;

import com.ssafy.i10a709be.common.security.jwt.JwtProvider;
import com.ssafy.i10a709be.common.exception.InternalServerException;
import com.ssafy.i10a709be.domain.member.dto.MemberUpdateRequestDto;
import com.ssafy.i10a709be.domain.member.entity.Member;
import com.ssafy.i10a709be.domain.member.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    /*
        Member Login
        만약, 해당 Member가 DB에 존재하지 않는다면 DB에 회원정보를 추가(최초 회원가입)
        유저 확인 후 해당 유저의 UUID를 이용해 Access Token과 Refresh Token을 발급해 return
     */
    @Override
    public List<String> login(Member member) {
        Optional<Member> result = memberRepository.findByEmail(member.getEmail());
        if( result.isEmpty() ){
            memberRepository.save(member);
        }
        List<String> tokens = jwtProvider.generateToken( result.isPresent() ? result.get().getMemberId(): member.getMemberId());
        tokens.add( result.isPresent() ? result.get().getMemberId(): member.getMemberId());
        member.updateRefreshToken(tokens.get(1));

        return tokens;
    }

    /*
        Member UUID를 이용해 Soft delete 실행
     */
    @Override
    @Transactional
    public boolean removeMember(String memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
        if(member.isPresent()){
            memberRepository.delete(member.get());
            return true;
        }

        return false;
    }

    @Override
    public Member findMemberById(String memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));
    }

    @Override
    public Member findMemberByEmail(String email) {
        Optional<Member> findMember = memberRepository.findByEmail( email );
        return Optional.of( findMember ).get().orElseThrow( () -> { throw new InternalServerException( "email에 해당하는 회원을 찾을 수 없습니다", this );}) ;
    }

    @Override
    @Transactional
    public Member updateMemberDetails(String memberId, MemberUpdateRequestDto memberUpdateRequestDto) {
        Member member = findMemberById(memberId);

        member.updateDetails(memberUpdateRequestDto);

        return member;
    }

    @Override
    @Transactional
    public void logout(String memberId) {
        memberRepository.findById(memberId).ifPresent(member ->{
            member.updateRefreshToken(null);
        });
    }
}
