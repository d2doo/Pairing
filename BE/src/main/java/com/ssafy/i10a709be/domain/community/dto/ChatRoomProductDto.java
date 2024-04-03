package com.ssafy.i10a709be.domain.community.dto;


import com.ssafy.i10a709be.domain.product.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomProductDto {
    private Long productId;
    private String title;
    private String imgSrc;

    public static ChatRoomProductDto fromEntity(Product product) {
        return ChatRoomProductDto.builder()
                .productId(product.getProductId())
                .title(product.getTitle())
                .imgSrc(product.getUnits().get(0).getUnitImages().get(0).getFiles().getSource())
                .build();
    }
}
