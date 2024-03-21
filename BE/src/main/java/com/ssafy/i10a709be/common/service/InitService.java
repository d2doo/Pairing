package com.ssafy.i10a709be.common.service;


import com.ssafy.i10a709be.domain.community.dto.ChatRoomCreateDto;
import com.ssafy.i10a709be.domain.community.enums.ChatRoomStatus;
import com.ssafy.i10a709be.domain.community.repository.ChatRoomRepository;
import com.ssafy.i10a709be.domain.community.service.ChatService;
import com.ssafy.i10a709be.domain.member.entity.Member;
import com.ssafy.i10a709be.domain.member.enums.OAuthProvider;
import com.ssafy.i10a709be.domain.member.repository.MemberRepository;
import com.ssafy.i10a709be.domain.product.entity.Category;
import com.ssafy.i10a709be.domain.product.entity.PartType;
import com.ssafy.i10a709be.domain.product.repository.CategoryRepository;
import com.ssafy.i10a709be.domain.product.repository.PartTypeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InitService {

    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final PartTypeRepository partTypeRepository;
    private final ChatService chatService;
    private final ChatRoomRepository chatRoomRepository;

    @PostConstruct
    @Transactional
    public void insertInit(){
        Member member = Member.builder()
                .email("cqqudgjs@naver.com")
                .nickname("라이빵허")
                .provider(OAuthProvider.KAKAO)
                .build();
        member.updateRefreshToken("eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJFeG9kaWEiLCJ1dWlkIjoiNDhiZWY2MzYtZGZkNy00YmM3LTk4ZjUtNTIzMmM0MjZlYzAxIiwiaWF0IjoxNzEwODA2NTIwLCJleHAiOjE3MTE0MTEzMjB9.x67DUg4brARUkfCFT66t89lZqooj0cmWWxd0Lj9glpM");
        memberRepository.save( member );
        Member findMember = memberRepository.findByEmail("cqqudgjs@naver.com").get();

        Member member2 = Member.builder()
                .email("amuva@naver.com")
                .nickname("윤주짜이")
                .provider(OAuthProvider.KAKAO)
                .build();
        memberRepository.save( member2 );

        Member member3 = Member.builder()
                .email("hyuniqque@gmail.com")
                .nickname("김다이헌")
                .provider(OAuthProvider.KAKAO)
                .build();
        memberRepository.save( member3 );



        log.info( findMember.getEmail() + " 테스트 데이터 등록 완료 id: " + member.getMemberId() + " " + member2.getMemberId() + " " + member3.getMemberId() + " ClassName: "+ this.getClass().getName() );

        List<Member> memberList = new ArrayList<>();
        memberList.add( member );
        memberList.add( member2 );
        memberList.add( member3 );
        ChatRoomCreateDto dto = new ChatRoomCreateDto( memberList, member.getMemberId(), "테스트방", 3, ChatRoomStatus.active );
        System.out.println( dto );
        chatService.createChatRoom( dto );
        log.info( findMember.getEmail() + " 테스트 데이터 등록 완료 id: " + member.getMemberId() + " ClassName: "+ this.getClass().getName() );

        Category category = Category.builder()
                .mainCategory("무선 이어폰")
                .subCategory("에어팟 프로 2")
                .build();

        categoryRepository.save(category);

        PartType partType = PartType.builder()
                .category(category)
                .position("왼쪽")
                .build();

        PartType partType2 = PartType.builder()
                .category(category)
                .position("오른쪽")
                .build();

        partTypeRepository.save(partType);
        partTypeRepository.save(partType2);
    }
}
