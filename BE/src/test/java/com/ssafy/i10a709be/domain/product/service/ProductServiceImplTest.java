package com.ssafy.i10a709be.domain.product.service;

import com.amazonaws.services.s3.model.JSONOutput;
import com.ssafy.i10a709be.domain.community.service.ChatService;
import com.ssafy.i10a709be.domain.deal.service.DealServiceImpl;
import com.ssafy.i10a709be.domain.member.entity.Member;
import com.ssafy.i10a709be.domain.member.repository.MemberRepository;
import com.ssafy.i10a709be.domain.product.dto.ProductSaveRequestDto;
import com.ssafy.i10a709be.domain.product.dto.UnitSaveRequestDto;
import com.ssafy.i10a709be.domain.product.entity.Product;
import com.ssafy.i10a709be.domain.product.entity.Unit;
import com.ssafy.i10a709be.domain.product.enums.ProductStatus;
import com.ssafy.i10a709be.domain.product.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductServiceImplTest {

    //대상 객체
    @Autowired
    ProductServiceImpl productService;


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
    void 기존_합의에서_새로운_합의를_생성하면_기존_합의들은_삭제되어있고_신규_상품이_묶여서_생성된다(){
        //멤버 불러오고
        Member member = memberRepository.findByEmail("cqqudgjstest@naver.com").get();
        Member member2 = memberRepository.findByEmail("amuvatest@naver.com").get();
        Product product4 = productRepository.findById(4L).get();
        // 각 상품 가져오고
        Member member3 = memberRepository.findByEmail("hyuniqquetest@gmail.com").get();
        Product product = productRepository.findById(1L).get();
        Product product2 = productRepository.findById(2L).get();
        Product product3 = productRepository.findById(3L).get();
        // 기존 결합 상품 해제하고
        dealService.rejectConfirm( product4.getProductId(), member.getMemberId() );
        assertThat(product.getStatus()).isEqualTo(ProductStatus.ON_SELL);
        assertThat(product2.getStatus()).isEqualTo(ProductStatus.ON_SELL);
        assertThat(product3.getStatus()).isEqualTo(ProductStatus.ON_SELL);

        assertThat(product4.getStatus()).isEqualTo(ProductStatus.PENDING);
        assertThat(product4.getIsDeleted()).isEqualTo(true);
        // 검증하고
        Unit unit = unitRepository.findUnitByProduct_ProductIdAndMember_MemberId( 1L, member.getMemberId());
        Unit unit2 = unitRepository.findUnitByProduct_ProductIdAndMember_MemberId( 2L, member2.getMemberId());
        Unit unit3 = unitRepository.findUnitByProduct_ProductIdAndMember_MemberId( 3L, member3.getMemberId());

        assertThat(unit.getOriginalProductId()).isEqualTo(unit.getProduct().getProductId());
        assertThat(unit2.getOriginalProductId()).isEqualTo(unit2.getProduct().getProductId());
        assertThat(unit3.getOriginalProductId()).isEqualTo(unit3.getProduct().getProductId());

        // 새로 합의를 만들자
        UnitSaveRequestDto udto = UnitSaveRequestDto.builder()
                .price(unit.getPrice())
                .age(unit.getAge())
                .categoryId(unit.getCategory().getCategoryId())
                .partTypeIds(Arrays.stream(new Long[]{1L}).toList())
                .status(unit.getStatus())
                .isCombinable(false)
                .unitDescription(unit.getUnitDescription())
                .build();
        List< Long > units = Arrays.stream(new Long[]{ 2L, 3L}).toList();
        ProductSaveRequestDto pdto = ProductSaveRequestDto.builder()
                .productTitle("기존 신규 합의 생성 테스트")
                .thumbnailIndex( 1 )
                .unit(udto)
                .targetUnits( units )
                .build();

        Long savedId = productService.createAfterCompose( member.getMemberId(), product.getProductId(),pdto);

        assertThat( product.getIsDeleted() ).isEqualTo( true );
        assertThat( product.getStatus()).isEqualTo( ProductStatus.PENDING );
        assertThat( savedId ).isNotNull();

        Product newProduct = productRepository.findById( savedId ).get();

        //검증
        assertThat( newProduct ).isNotNull();
        assertThat( newProduct.getUnits() ).contains( unit );
        assertThat( newProduct.getUnits()).contains( unit2 );
        assertThat( newProduct.getUnits()).contains( unit3 );
        assertThat( newProduct.getStatus() ).isEqualTo( ProductStatus.ON_SELL );

        assertThat( unit2.getProduct().getProductId() ).isEqualTo( newProduct.getProductId() );
    }


}