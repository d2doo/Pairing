package com.ssafy.i10a709be.domain.product.dto;

import com.ssafy.i10a709be.domain.product.entity.Product;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductSaveReqDto {
    private String productTitle;
    private UnitSaveReqDto unit;
    private List<Long> targetUnits;
    private Integer thumbnailIndex;
}