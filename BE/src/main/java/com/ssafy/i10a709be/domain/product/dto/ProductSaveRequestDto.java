package com.ssafy.i10a709be.domain.product.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductSaveRequestDto {
    private String productTitle;
    private UnitSaveRequestDto unit;
    private List<Long> targetUnits;
    private Integer thumbnailIndex;
}