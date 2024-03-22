package com.ssafy.i10a709be.domain.product.service;

import com.ssafy.i10a709be.common.exception.InternalServerException;
import com.ssafy.i10a709be.common.exception.NoAuthorizationException;
import com.ssafy.i10a709be.domain.member.entity.Member;
import com.ssafy.i10a709be.domain.member.repository.MemberRepository;
import com.ssafy.i10a709be.domain.product.dto.ProductSaveRequestDto;
import com.ssafy.i10a709be.domain.product.entity.*;
import com.ssafy.i10a709be.domain.product.enums.ProductStatus;
import com.ssafy.i10a709be.domain.product.repository.CategoryRepository;
import com.ssafy.i10a709be.domain.product.repository.PartTypeRepository;
import com.ssafy.i10a709be.domain.product.repository.ProductRepository;
import com.ssafy.i10a709be.domain.product.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UnitRepository unitRepository;
    private final MemberRepository memberRepository;
    private final PartTypeRepository partTypeRepository;
    private final CategoryRepository categoryRepository;


    //TODO 1차 개발 끝나면 해당 로직 세분화를 시켜서 재사용성을 높히자.
    //단일 파츠 및 유닟 및 상품 생성
    @Override
    @Transactional
    public Product saveProduct(String memberId, ProductSaveRequestDto request) {
        Optional<Member> member = memberRepository.findById(memberId);
        Optional<Category> category = categoryRepository.findById(request.getUnit().getCategoryId());

        if (member.isPresent() && category.isPresent()) {
            Product product = Product.builder()
                    .member(member.get())
                    .title(request.getProductTitle())
                    .status(ProductStatus.ON_SELL)
                    .maxAge(request.getUnit().getAge())
                    .totalPrice(request.getUnit().getPrice())
                    .build();

            Unit unit = Unit.builder()
                    .member(member.get())
                    .product(product)
                    .originalProductId(product.getProductId())
                    .category(category.get())
                    .isCombinable(request.getUnit().getIsCombinable())
                    .unitDescription(request.getUnit().getUnitDescription())
                    .price(request.getUnit().getPrice())
                    .age(request.getUnit().getAge())
                    .isConfirmed( true )
                    .build();

            product.getUnits().add(unit);

            for (Long partTypeId : request.getUnit().getPartTypeIds()) {
                Optional<PartType> partType = partTypeRepository.findById(partTypeId);

                if (partType.isPresent()) {

                    Part part = Part.builder()
                            .unit(unit)
                            .partType(partType.get())
                            .build();

                    unit.getParts().add(part);
                } else {
                    throw new IllegalArgumentException();
                }
            }

            productRepository.save(product);

            if (!request.getTargetUnits().isEmpty())
                return composeUnits(unit, request.getTargetUnits());

            return product;
        }

        throw new IllegalArgumentException();
    }

    //합의시 일때 실행되는 로직
    @Override
    public Product composeUnits(Unit unit, List<Long> targets) {
        Product product = Product.builder()
                .member(unit.getMember())
                .status(ProductStatus.PENDING)
                .build();
        
        // 본인이 애초에 합의를 열었기에 true에서 바꿀 필요가 없다.
        unit.updateProduct(product);
        product.getUnits().add(unit);

        for (Long targetUnitId : targets){
            unitRepository.findById(targetUnitId).ifPresent(
                    targetUnit -> {
                        targetUnit.getProduct().softDeleted(true);//targetUnit의 원래 product의 isdeleted는 true로 바껴야함
                        targetUnit.setIsConfirmed( false ); // 나머지 친구들은 거절
                        targetUnit.updateProduct(product);
                        product.getUnits().add(targetUnit);
                    }
            );
        }

        productRepository.save(product);

        return product;
    }

    @Override
    public List<Product> findAllProduct(Boolean isCombined, String nickname, String memberId, Long categoryId, String productStatus, Integer startPrice, Integer endPrice, Integer maxAge, String keyword) {
        return productRepository.findProductsByDynamicQuery(isCombined, nickname, memberId, categoryId, productStatus, startPrice, endPrice, maxAge, keyword);
    }

    @Override
    public Product findProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
    }

    @Override
    @Transactional
    public String modifyProduct(Long productId, String productTitle) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        product.modifyTitle(productTitle);

        return product.getTitle();
    }

    @Override
    @Transactional
    public void deleteProduct(String memberId, Long productId) {
        Product product = productRepository.deleteProductById(productId, memberId).orElseThrow(() -> new IllegalArgumentException("해당상품이 존재하지않습니다."));
        List<Unit> units = product.getUnits();
        product.softDeleted(Boolean.TRUE);

        // 단일 판매일 경우와 묶음 판매일 경우 로직 상이
        if (units.size() == 1) {
            units.get(0).softDeleted(Boolean.TRUE);
        } else {
            for (Unit unit : units) {
                Product originalProduct = productRepository.findById(unit.getOriginalProductId())
                        .orElseThrow(() -> new IllegalArgumentException("문제가 계속될 시 wntjrdbs@gmail.com으로 연락주세요."));
                unit.updateProduct(originalProduct);
            }
        }
    }
    //Compose 생성 로직
    @Transactional
    @Override
    public Long createAfterCompose(String memberId, Long productId, ProductSaveRequestDto productSaveRequestDto) {
        Member member = memberRepository.findById( memberId ).orElseThrow( () -> new NoAuthorizationException("해당 사용자가 없습니다.", this));
        Product product = productRepository.findProductAndUnitsByProductId( productId ).orElseThrow( () -> new IllegalArgumentException("잘못된 상품 정보 요청입니다."));
        product.softDeleted( true );
        product.updateStatus(ProductStatus.PENDING);
        Unit unit = unitRepository.findUnitByProduct_ProductIdAndMember_MemberId( productId, memberId );
        // 권한 체크
        if( member.getMemberId() != product.getMember().getMemberId() ) throw new NoAuthorizationException( "잘못된 요청입니다.", this );
        // 재사용
        Product newProduct = Product.builder()
                .member(member)
                .title(productSaveRequestDto.getProductTitle())
                .status(ProductStatus.ON_SELL)
                .maxAge(productSaveRequestDto.getUnit().getAge())
                .totalPrice(productSaveRequestDto.getUnit().getPrice())
                .build();

        newProduct.getUnits().add( unit );
        unit.updateProduct( newProduct );

        Product saved = productRepository.save(newProduct);
        
        for (Long targetUnitId : productSaveRequestDto.getTargetUnits()){
            unitRepository.findById(targetUnitId).ifPresent(
                    targetUnit -> {
                        targetUnit.getProduct().softDeleted(true);//targetUnit의 원래 product의 isdeleted는 true로 바껴야함
                        targetUnit.setIsConfirmed( false ); // 나머지 친구들은 거절
                        targetUnit.updateProduct(newProduct);
                        newProduct.getUnits().add(targetUnit);
                    }
            );
        }
//        composeUnits( unit, productSaveRequestDto.getTargetUnits());

        return saved.getProductId();
    }
}