package com.ssafy.i10a709be.domain.product.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@ToString
public class ProductSaveRequestDto {
    private String productTitle;
    private UnitSaveRequestDto unit;
    private List<Long> targetUnits;
    private Integer thumbnailIndex;
}