package com.ssafy.i10a709be.common.service;


import com.ssafy.i10a709be.domain.community.dto.ChatRoomCreateDto;
import com.ssafy.i10a709be.domain.community.enums.ChatRoomStatus;
import com.ssafy.i10a709be.domain.community.repository.ChatRoomRepository;
import com.ssafy.i10a709be.domain.community.service.ChatService;
import com.ssafy.i10a709be.domain.member.entity.Member;
import com.ssafy.i10a709be.domain.member.enums.OAuthProvider;
import com.ssafy.i10a709be.domain.member.repository.MemberRepository;
import com.ssafy.i10a709be.domain.product.entity.*;
import com.ssafy.i10a709be.domain.product.enums.ProductStatus;
import com.ssafy.i10a709be.domain.product.repository.*;
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
    private final PartRepository partRepository;
    private final UnitRepository unitRepository;
    private final ProductRepository productRepository;

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
        PartType partType3 = PartType.builder()
                .category(category)
                .position("케이스")
                .build();

        partTypeRepository.save(partType);
        partTypeRepository.save(partType2);
        partTypeRepository.save(partType3);

        Product testP = Product.builder()
                .title("test1")
                .member(member)
                .status(ProductStatus.PENDING)
                .maxAge(1)
                .totalPrice(10000)
                .build();

        Product testP2 = Product.builder()
                .title("test2")
                .member(member2)
                .status(ProductStatus.PENDING)
                .maxAge(2)
                .totalPrice(20000)
                .build();
        testP2.softDeleted(true);

        Product testP3 = Product.builder()
                .title("test3")
                .member(member3)
                .status(ProductStatus.PENDING)
                .maxAge(3)
                .totalPrice(30000)
                .build();
        testP3.softDeleted(true);

        Product testP4 = Product.builder()
                .title("test14")
                .member(member)
                .status(ProductStatus.PENDING)
                .maxAge(3)
                .totalPrice(60000)
                .build();

        productRepository.save( testP );
        productRepository.save( testP2 );
        productRepository.save( testP3 );
        productRepository.save( testP4 );

        Unit testU = Unit.builder()
                .age(1)
                .price(10000)
                .member(member)
                .isCombinable(true)
                .category(category)
                .originalProductId(testP.getProductId())
                .isConfirmed(true)
                .unitDescription("테스트 유닛1")
                .product(testP4)
                .build();

        Unit testU2 = Unit.builder()
                .age(2)
                .price(20000)
                .member(member2)
                .isCombinable(true)
                .category(category)
                .originalProductId(testP2.getProductId())
                .isConfirmed(false)
                .unitDescription("테스트 유닛2")
                .product(testP4)
                .build();

        Unit testU3 = Unit.builder()
                .age(3)
                .price(30000)
                .member(member3)
                .isCombinable(true)
                .category(category)
                .originalProductId(testP3.getProductId())
                .isConfirmed(false)
                .unitDescription("테스트 유닛3")
                .product(testP4)
                .build();

        unitRepository.save( testU );
        unitRepository.save( testU2 );
        unitRepository.save( testU3 );

        Part testPart = Part.builder()
                .partType(partType)
                .unit(testU)
                .build();
        Part testPart2 = Part.builder()
                .partType(partType2)
                .unit(testU2)
                .build();
        Part testPart3 = Part.builder()
                .partType(partType3)
                .unit(testU3)
                .build();

        partRepository.save(testPart);
        partRepository.save(testPart2);
        partRepository.save(testPart3);

        log.info( findMember.getEmail() + " 테스트 데이터 등록 완료" );
    }
}
