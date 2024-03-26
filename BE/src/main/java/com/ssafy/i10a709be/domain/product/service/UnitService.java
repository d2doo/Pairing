package com.ssafy.i10a709be.domain.product.service;

import com.ssafy.i10a709be.domain.product.dto.UnitUpdateRequestDto;
import com.ssafy.i10a709be.domain.product.entity.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UnitService {
    Unit findUnitById(Long unitId);

    Page<Unit> findUnitsByPartTypeId(Pageable pageable, Long unitId, Long partTypeId);

    Unit updateUnitById(String memberId, Long unitId, UnitUpdateRequestDto unitUpdateRequestDto);
}
