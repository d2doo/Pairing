package com.ssafy.i10a709be.domain.product.dto;

import com.ssafy.i10a709be.domain.product.entity.Category;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CategoryDto {
    private Long categoryId;
    private String mainCategory;
    private String subCategory;
    private List<PartTypeDto> partTypes;

    public static CategoryDto fromEntity(Category category) {
        return CategoryDto.builder()
                .categoryId(category.getCategoryId())
                .mainCategory(category.getMainCategory())
                .subCategory(category.getSubCategory())
                .partTypes(category.getPartTypes().stream().map(PartTypeDto::fromEntity).toList())
                .build();
    }
}