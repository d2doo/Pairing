package com.ssafy.i10a709be.domain.product.dto;

import lombok.Data;

@Data
public class UnitResponseDto {
    private long unitId;
    private long productId;
    private long categoryId;
    private boolean isCombinable;
    private String unitDescription;
    private int price;
    private int age;
}