package com.ssafy.i10a709be.domain.product.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Builder
@Data
@ToString
public class UnitSaveRequestDto {
    private Long categoryId;
    private Boolean isCombinable;
    private String unitDescription;
    private Integer price;
    private Integer age;
    private List<Long> images;
    private String status;
    private List<Long> partTypeIds;
}
