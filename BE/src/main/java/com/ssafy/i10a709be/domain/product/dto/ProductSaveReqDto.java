package com.ssafy.i10a709be.domain.product.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductSaveReqDto {
    private String productTitle;
    private UnitSaveReqDto unit;
    private List<Long> targetUnits;
    private Integer thumbnailIndex;
}