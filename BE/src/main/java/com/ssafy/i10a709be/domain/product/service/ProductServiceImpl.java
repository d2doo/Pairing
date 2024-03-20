package com.ssafy.i10a709be.domain.product.service;

import com.ssafy.i10a709be.domain.member.entity.Member;
import com.ssafy.i10a709be.domain.member.repository.MemberRepository;
import com.ssafy.i10a709be.domain.product.dto.ProductSaveReqDto;
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

    @Override
    @Transactional
    public Product saveProduct(String memberId, ProductSaveReqDto request) {
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

    @Override
    public Product composeUnits(Unit unit, List<Long> targets) {
        Product product = Product.builder()
                .member(unit.getMember())
                .status(ProductStatus.PENDING)
                .build();

        unit.updateProduct(product);
        product.getUnits().add(unit);

        for (Long targetUnitId : targets){
            unitRepository.findById(targetUnitId).ifPresent(
                    targetUnit -> {
                        targetUnit.updateProduct(product);
                        product.getUnits().add(targetUnit);
                    }
            );
        }

        productRepository.save(product);

        return product;
    }
}