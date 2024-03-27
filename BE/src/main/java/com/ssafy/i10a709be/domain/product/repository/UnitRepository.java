package com.ssafy.i10a709be.domain.product.repository;

import com.querydsl.core.BooleanBuilder;
import com.ssafy.i10a709be.domain.product.entity.QPart;
import com.ssafy.i10a709be.domain.product.entity.QPartType;
import com.ssafy.i10a709be.domain.product.entity.QUnit;
import com.ssafy.i10a709be.domain.product.entity.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface UnitRepository extends JpaRepository<Unit, Long>, QuerydslPredicateExecutor<Unit> {
    Unit findUnitByProduct_ProductIdAndMember_MemberId(Long productId, String memberId);

    Optional<Unit> findByUnitIdAndMember_MemberId(Long unitId, String memberId);

    default Page<Unit> findUnitsByPartTypeId(Pageable pageable, Long unitId, Long partTypeId) {
        QUnit unit = QUnit.unit;

        BooleanBuilder builder = new BooleanBuilder();

        if (unitId != null) builder.and(unit.unitId.gt(unitId));
        builder.and(unit.parts.size().eq(1));
        builder.and(unit.parts.any().partType.partTypeId.eq(partTypeId));

        return findAll(builder, pageable);
    }
}