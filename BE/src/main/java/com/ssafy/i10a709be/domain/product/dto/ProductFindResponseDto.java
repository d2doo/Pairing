package com.ssafy.i10a709be.domain.product.dto;

import com.ssafy.i10a709be.domain.member.dto.MemberSummaryResponseDto;
import com.ssafy.i10a709be.domain.product.entity.Product;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductFindResponseDto {
    private Long productId;
    private MemberSummaryResponseDto leader;
    private String productTitle;
    private String thumbnailUrl;
    private CategoryDto category;
    private List<UnitFindDto> units;
    private Integer totalPrice;
    private Integer maxAge;

    public static ProductFindResponseDto fromEntity(Product product){
        return ProductFindResponseDto.builder()
                .productId(product.getProductId())
                .leader(MemberSummaryResponseDto.fromEntity(product.getMember()))
                .productTitle(product.getTitle())
                .thumbnailUrl(product.getUnits().getFirst().getUnitImages().getFirst().getFiles().getSource())
                .category(CategoryDto.fromEntity(product.getUnits().getFirst().getCategory()))
                .units(product.getUnits().stream().map(UnitFindDto::fromEntity).toList())
                .totalPrice(product.getTotalPrice())
                .maxAge(product.getMaxAge())
                .build();
    }
}