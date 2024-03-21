package com.ssafy.i10a709be.domain.product.dto;

import lombok.Data;

@Data
public class UnitUpdateRequestDto {
    private boolean isCombinable;
    private String unitDescription;
    private int price;
    private int age;
    private String status;
}
