package com.ssafy.i10a709be.domain.product.service;

import com.ssafy.i10a709be.domain.product.dto.UnitResponseDto;
import com.ssafy.i10a709be.domain.product.dto.UnitSaveRequestDto;
import com.ssafy.i10a709be.domain.product.dto.UnitUpdateRequestDto;
import com.ssafy.i10a709be.domain.product.entity.Unit;
import com.ssafy.i10a709be.domain.product.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UnitServiceImpl implements UnitService {

    private final UnitRepository unitRepository;

    @Override
    public Unit findUnitById(Long unitId) {
        return unitRepository.findById(unitId).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Page<Unit> findUnitsByPartTypeId(Pageable pageable, Long unitId, Long partTypeId) {
        return unitRepository.findUnitsByPartTypeId(pageable, unitId, partTypeId);
    }

    @Override
    @Transactional
    public Unit updateUnitById(String memberId, Long unitId, UnitUpdateRequestDto unitUpdateRequestDto) {
        Unit unit = unitRepository.findByUnitIdAndMember_MemberId(unitId, memberId).orElseThrow(IllegalArgumentException::new);

        unit.updateDetails(
                unitUpdateRequestDto.getIsCombinable(),
                unitUpdateRequestDto.getUnitDescription(),
                unitUpdateRequestDto.getPrice(),
                unitUpdateRequestDto.getAge(),
                unitUpdateRequestDto.getStatus()
        );

        return unit;
    }

    @Override
    public List<Unit> findMyUnits(String memberId) {
        return unitRepository.findAllByMember_MemberId( memberId );
    }


}