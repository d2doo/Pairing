package com.ssafy.i10a709be.domain.product.controller;

import com.ssafy.i10a709be.domain.product.dto.ProductFindResDto;
import com.ssafy.i10a709be.domain.product.dto.ProductSaveReqDto;
import com.ssafy.i10a709be.domain.product.entity.Product;
import com.ssafy.i10a709be.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Void> saveProduct(@AuthenticationPrincipal String memberId, @RequestBody ProductSaveReqDto productSaveReqDto) {
        try {
            Product product = productService.saveProduct(memberId, productSaveReqDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductFindResDto> findProduct(@PathVariable Long productId, @AuthenticationPrincipal String memberId) {
        Product product = productService.findProduct(productId);

        return ResponseEntity.ok().body(ProductFindResDto.fromEntity(product));
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<String> modifyProductTitle(@PathVariable Long productId, @RequestParam String productTitle) {
        return ResponseEntity.ok().body(productService.modifyProduct(productId, productTitle));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@AuthenticationPrincipal String memberId, @PathVariable Long productId){
        productService.deleteProduct(memberId, productId);

        return ResponseEntity.ok().build();
    }
}