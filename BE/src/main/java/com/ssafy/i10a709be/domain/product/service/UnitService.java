package com.ssafy.i10a709be.domain.product.service;

import com.ssafy.i10a709be.domain.product.dto.UnitUpdateRequestDto;
import com.ssafy.i10a709be.domain.product.entity.Unit;

public interface UnitService {
    Unit findUnitById(Long unitId);

    Unit updateUnitById(String memberId, Long unitId, UnitUpdateRequestDto unitUpdateRequestDto);
}
