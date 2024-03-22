package com.ssafy.i10a709be.domain.product.dto;

import com.ssafy.i10a709be.domain.product.entity.Unit;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnitResponseDto {
    private Long unitId;
    private Long productId;
    private Long categoryId;
    private Boolean isCombinable;
    private String unitDescription;
    private Integer price;
    private Integer age;
    private String status;

    public static UnitResponseDto fromEntity(Unit unit) {
        return UnitResponseDto.builder()
                .unitId(unit.getUnitId())
                .productId(unit.getProduct().getProductId())
                .categoryId(unit.getCategory().getCategoryId())
                .isCombinable(unit.getIsCombinable())
                .unitDescription(unit.getUnitDescription())
                .price(unit.getPrice())
                .age(unit.getAge())
                .status(unit.getStatus())
                .build();
    }
}