package com.ssafy.i10a709be.domain.product.dto;

import com.ssafy.i10a709be.domain.product.entity.PartType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PartTypeDto {
    private Long partTypeId;
    private String position;

    public static PartTypeDto fromEntity(PartType partType) {
        return PartTypeDto.builder()
                .partTypeId(partType.getPartTypeId())
                .position(partType.getPosition())
                .build();
    }
}