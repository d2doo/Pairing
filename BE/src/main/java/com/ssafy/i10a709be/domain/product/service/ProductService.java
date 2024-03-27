package com.ssafy.i10a709be.domain.product.service;

import com.ssafy.i10a709be.domain.product.dto.ProductSaveRequestDto;
import com.ssafy.i10a709be.domain.product.entity.Product;
import com.ssafy.i10a709be.domain.product.entity.Unit;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Product saveProduct(String memberId, ProductSaveRequestDto request);

    Product composeUnits(Unit unit, List<Long> targets);

    Page<Product> findAllProduct(Pageable pageable, Long productId, Boolean isCombined, String nickname, String memberId, Long categoryId, String productStatus, Integer startPrice, Integer endPrice, Integer maxAge, String keyword);

    Product findProduct(Long productId);

    String modifyProduct(Long productId, String productTitle);

    void deleteProduct(String memebrId, Long productId);


    Long createAfterCompose(String memberId, Long productId ,ProductSaveRequestDto productSaveRequestDto);
}
