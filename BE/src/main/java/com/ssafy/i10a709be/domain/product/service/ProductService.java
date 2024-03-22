package com.ssafy.i10a709be.domain.product.service;

import com.ssafy.i10a709be.domain.product.dto.ProductSaveRequestDto;
import com.ssafy.i10a709be.domain.product.entity.Product;
import com.ssafy.i10a709be.domain.product.entity.Unit;

import java.util.List;

public interface ProductService {
    Product saveProduct(String memberId, ProductSaveRequestDto request);

    Product composeUnits(Unit unit, List<Long> targets);

    List<Product> findAllProduct(Boolean isCombined, String nickname, String memberId, Long categoryId, String productStatus, Integer startPrice, Integer endPrice, Integer maxAge, String keyword);

    Product findProduct(Long productId);

    String modifyProduct(Long productId, String productTitle);

    void deleteProduct(String memebrId, Long productId);


    Long createAfterCompose(String memberId, Long productId ,ProductSaveRequestDto productSaveRequestDto);
}
