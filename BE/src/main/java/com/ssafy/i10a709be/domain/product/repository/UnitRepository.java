package com.ssafy.i10a709be.domain.product.repository;

import com.querydsl.core.BooleanBuilder;
import com.ssafy.i10a709be.domain.product.entity.QPart;
import com.ssafy.i10a709be.domain.product.entity.QPartType;
import com.ssafy.i10a709be.domain.product.entity.QUnit;
import com.ssafy.i10a709be.domain.product.entity.Unit;
import com.ssafy.i10a709be.domain.product.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface UnitRepository extends JpaRepository<Unit, Long>, QuerydslPredicateExecutor<Unit> {
    Unit findUnitByProduct_ProductIdAndMember_MemberId(Long productId, String memberId);

    Optional<Unit> findByUnitIdAndMember_MemberId(Long unitId, String memberId);

    List<Unit> findAllByMember_MemberId( String memberId );

    default Page<Unit> findUnitsByPartTypeId(Pageable pageable, Long unitId, Long partTypeId) {
        QUnit unit = QUnit.unit;
        ProductStatus onSellStatus = ProductStatus.ON_SELL;

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(unit.isDeleted.eq(false));
        builder.and(unit.isCombinable.eq(true));

        if (unitId != null) builder.and(unit.unitId.lt(unitId));

        builder.and(unit.parts.size().eq(1));
        builder.and(unit.parts.any().partType.partTypeId.eq(partTypeId));
        builder.and(unit.product.productId.eq(unit.originalProductId));
        builder.and(unit.product.status.eq(onSellStatus));

        return findAll(builder, pageable);
    }
}