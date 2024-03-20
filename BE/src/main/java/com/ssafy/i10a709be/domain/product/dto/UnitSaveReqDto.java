package com.ssafy.i10a709be.domain.product.dto;

import lombok.Data;

import java.util.List;

@Data
public class UnitSaveReqDto {
    private Long categoryId;
    private Boolean isCombinable;
    private String unitDescription;
    private Integer price;
    private Integer age;
    private String status;
    private List<Long> partTypeIds;
}
