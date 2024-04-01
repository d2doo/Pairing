package com.ssafy.i10a709be.domain.product.controller.restcontroller;

import com.ssafy.i10a709be.domain.product.dto.UnitResponseDto;
import com.ssafy.i10a709be.domain.product.dto.UnitSaveRequestDto;
import com.ssafy.i10a709be.domain.product.dto.UnitUpdateRequestDto;
import com.ssafy.i10a709be.domain.product.entity.Unit;
import com.ssafy.i10a709be.domain.product.service.UnitService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/unit")
public class UnitRestController {
    private final UnitService unitService;

    @GetMapping
    public ResponseEntity<List<UnitResponseDto>> findAllUnits(
            @RequestParam int size,
            @RequestParam(required = false) Long unitId,
            @RequestParam Long partTypeId
    ) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "unitId"));
        return ResponseEntity.ok(unitService.findUnitsByPartTypeId(pageable, unitId, partTypeId).stream().map(UnitResponseDto::fromEntity).toList());
    }

    @GetMapping("/{unitId}")
    public ResponseEntity<UnitResponseDto> findUnitById(@PathVariable Long unitId) {
        return ResponseEntity.ok(UnitResponseDto.fromEntity(unitService.findUnitById(unitId)));
    }

    @PutMapping("/{unitId}")
    public ResponseEntity<UnitResponseDto> updateUnitDetails(
            @AuthenticationPrincipal String memberId,
            @PathVariable Long unitId,
            @RequestBody UnitUpdateRequestDto unitUpdateRequestDto
    ) {
        return ResponseEntity.ok(UnitResponseDto.fromEntity(unitService.updateUnitById(memberId, unitId, unitUpdateRequestDto)));
    }

    @GetMapping("/unit/my")
    public ResponseEntity<List<UnitResponseDto>> findMyUnits( @AuthenticationPrincipal String memberId ){
        return ResponseEntity.ok( unitService.findMyUnits( memberId).stream().map( UnitResponseDto::fromEntity ).collect(Collectors.toList()));
    }



}