package com.ssafy.i10a709be.domain.deal.service;

import com.ssafy.i10a709be.common.exception.NoAuthorizationException;
import com.ssafy.i10a709be.domain.community.dto.ChatRoomCreateDto;
import com.ssafy.i10a709be.domain.community.enums.ChatRoomStatus;
import com.ssafy.i10a709be.domain.community.service.ChatService;
import com.ssafy.i10a709be.domain.member.entity.Member;
import com.ssafy.i10a709be.domain.member.enums.OAuthProvider;
import com.ssafy.i10a709be.domain.member.repository.MemberRepository;
import com.ssafy.i10a709be.domain.product.entity.*;
import com.ssafy.i10a709be.domain.product.enums.ProductStatus;
import com.ssafy.i10a709be.domain.product.repository.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class DealServiceImplTest {

    @Autowired
    DealServiceImpl dealService;

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ChatService chatService;
    @Autowired
    PartTypeRepository partTypeRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UnitRepository unitRepository;
    @Autowired
    PartRepository partRepository;

    @Test
    void 컴펌을_승인하면_해당_유닛의_상태는_true가_된다() {

        Member member = memberRepository.findByEmail("amuva@naver.com").get();
        Product product = productRepository.findById(4L).get();
        dealService.approveConfirm( product.getProductId(), member.getMemberId() );
        Unit target = unitRepository.findUnitByProduct_ProductIdAndMember_MemberId(product.getProductId(), member.getMemberId()) ;
        assertThat( target.getIsConfirmed() ).isEqualTo( true );


    }

    @Test
    void 컴펌을_승인했을_때_일부_true가_된다면_product의_상태는_PENDING이다() {
        Member member2 = memberRepository.findByEmail("amuva@naver.com").get();
        Product product = productRepository.findById(4L).get();

        dealService.approveConfirm( product.getProductId(), member2.getMemberId());
        assertThat( product.getStatus()).isEqualTo(ProductStatus.PENDING);
        assertThat( product.getUnits().size()).isGreaterThan( 1 );
    }

    @Test
    void 컴펌을_승인했을_때_전부_true가_된다면_product의_상태는_ON_SELL이다() {
        Member member2 = memberRepository.findByEmail("amuva@naver.com").get();
        Product product = productRepository.findById(4L).get();
        Member member3 = memberRepository.findByEmail("hyuniqque@gmail.com").get();


        dealService.approveConfirm( product.getProductId(), member2.getMemberId());
        dealService.approveConfirm( product.getProductId(), member3.getMemberId());


        assertThat( product.getStatus()).isEqualTo(ProductStatus.ON_SELL);
    }

    @Test
    void 컨펌을_거절하면_각_유닛의_product는_원래_product로_돌아가야_하고_각각_ON_SELL이어야_한다() {
        Member member = memberRepository.findByEmail("cqqudgjs@naver.com").get();
        Member member2 = memberRepository.findByEmail("amuva@naver.com").get();
        Product product4 = productRepository.findById(4L).get();


        Member member3 = memberRepository.findByEmail("hyuniqque@gmail.com").get();
        Product product1 = productRepository.findById(1L).get();
        Product product2 = productRepository.findById(2L).get();
        Product product3 = productRepository.findById(3L).get();

        dealService.rejectConfirm( product4.getProductId(), member.getMemberId() );
        assertThat(product1.getStatus()).isEqualTo(ProductStatus.ON_SELL);
        assertThat(product2.getStatus()).isEqualTo(ProductStatus.ON_SELL);
        assertThat(product3.getStatus()).isEqualTo(ProductStatus.ON_SELL);

        assertThat(product4.getStatus()).isEqualTo(ProductStatus.PENDING);
        assertThat(product4.getIsDeleted()).isEqualTo(true);

        Unit unit = unitRepository.findUnitByProduct_ProductIdAndMember_MemberId( 1L, member.getMemberId());
        Unit unit2 = unitRepository.findUnitByProduct_ProductIdAndMember_MemberId( 2L, member2.getMemberId());
        Unit unit3 = unitRepository.findUnitByProduct_ProductIdAndMember_MemberId( 3L, member3.getMemberId());

        assertThat(unit.getOriginalProductId()).isEqualTo(unit.getProduct().getProductId());
        assertThat(unit2.getOriginalProductId()).isEqualTo(unit2.getProduct().getProductId());
        assertThat(unit3.getOriginalProductId()).isEqualTo(unit3.getProduct().getProductId());
    }

    @Test
    void 권한이_없는_사용자가_바꾸려고_하면_NoAuthorizationException이_터져야_한다(){
        assertThatThrownBy( () -> {
            dealService.rejectConfirm(4L, "iamhacker");
        }).isInstanceOf(NoAuthorizationException.class);

    }

}