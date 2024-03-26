package com.ssafy.i10a709be.domain.product.dto;

import com.ssafy.i10a709be.domain.product.entity.Unit;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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
    private List<String> images;
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
                .images(unit.getUnitImages().stream().map(unitImage -> unitImage.getFiles().getSource()).toList())
                .status(unit.getStatus())
                .build();
    }
}