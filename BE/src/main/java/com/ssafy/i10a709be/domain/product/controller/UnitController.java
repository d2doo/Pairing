package com.ssafy.i10a709be.domain.product.controller;

import com.ssafy.i10a709be.domain.product.dto.UnitResponseDto;
import com.ssafy.i10a709be.domain.product.dto.UnitUpdateRequestDto;
import com.ssafy.i10a709be.domain.product.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/unit")
public class UnitController {
    private final UnitService unitService;

    @GetMapping("/{unitId}")
    public ResponseEntity<UnitResponseDto> findUnitById(@PathVariable Long unitId) {
        return ResponseEntity.ok(UnitResponseDto.fromEntity(unitService.findUnitById(unitId)));
    }

    @PatchMapping("/{unitId}")
    public ResponseEntity<UnitResponseDto> updateUnitDetails(
            @AuthenticationPrincipal String memberId,
            @PathVariable Long unitId,
            @RequestBody UnitUpdateRequestDto unitUpdateRequestDto
    ) {
        return ResponseEntity.ok(UnitResponseDto.fromEntity(unitService.updateUnitById(memberId, unitId, unitUpdateRequestDto)));
    }
}