package com.ssafy.i10a709be.domain.product.dto;

import lombok.Data;

@Data
public class UnitUpdateRequestDto {
    private Boolean isCombinable;
    private String unitDescription;
    private Integer price;
    private Integer age;
    private String status;
}
