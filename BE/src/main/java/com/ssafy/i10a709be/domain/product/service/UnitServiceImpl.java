package com.ssafy.i10a709be.domain.product.service;

import com.ssafy.i10a709be.domain.product.dto.UnitUpdateRequestDto;
import com.ssafy.i10a709be.domain.product.entity.Unit;
import com.ssafy.i10a709be.domain.product.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;

    @Override
    public Unit findUnitById(Long unitId) {
        return unitRepository.findById(unitId).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    @Transactional
    public Unit updateUnitById(String memberId, Long unitId, UnitUpdateRequestDto unitUpdateRequestDto) {
        Unit unit = unitRepository.findByUnitIdAndMember_MemberId(unitId, memberId).orElseThrow(IllegalArgumentException::new);

        unit.updateDetails(
                unitUpdateRequestDto.isCombinable(),
                unitUpdateRequestDto.getUnitDescription(),
                unitUpdateRequestDto.getPrice(),
                unitUpdateRequestDto.getAge(),
                unitUpdateRequestDto.getStatus()
        );

        return unit;
    }
}