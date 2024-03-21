package com.ssafy.i10a709be.domain.product.dto;

import com.ssafy.i10a709be.domain.member.dto.MemberSummaryResponseDto;
import com.ssafy.i10a709be.domain.product.entity.Product;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductFindResDto {
    private long productId;
    private MemberSummaryResponseDto leader;
    private String productTitle;
    private List<UnitFindDto> units;
    private int totalPrice;
    private int averageAge;

    public static ProductFindResDto fromEntity(Product product){
        return ProductFindResDto.builder()
                .productId(product.getProductId())
                .leader(MemberSummaryResponseDto.fromEntity(product.getMember()))
                .productTitle(product.getTitle())
                .units(product.getUnits().stream().map(UnitFindDto::fromEntity).toList())
                .totalPrice(product.getTotalPrice())
                .averageAge(product.getMaxAge())
                .build();
    }
}