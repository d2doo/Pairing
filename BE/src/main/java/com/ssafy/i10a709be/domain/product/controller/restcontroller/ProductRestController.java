package com.ssafy.i10a709be.domain.product.controller.restcontroller;

import com.ssafy.i10a709be.domain.product.dto.ProductFindResponseDto;
import com.ssafy.i10a709be.domain.product.dto.ProductSaveRequestDto;
import com.ssafy.i10a709be.domain.product.entity.Product;
import com.ssafy.i10a709be.domain.product.service.ProductService;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
public class ProductRestController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Void> saveProduct(@AuthenticationPrincipal String memberId, @RequestBody ProductSaveRequestDto productSaveRequestDto) {
        try {
            Product product = productService.saveProduct(memberId, productSaveRequestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductFindResponseDto>> findAllProduct(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Boolean isCombined,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) String memberId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String productStatus,
            @RequestParam(required = false) Integer startPrice,
            @RequestParam(required = false) Integer endPrice,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) String keyword
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("productId"));
        Page<Product> products = productService.findAllProduct(pageable, productId,isCombined, nickname, memberId, categoryId, productStatus, startPrice, endPrice, maxAge, keyword);

        List<ProductFindResponseDto> productFindResponseDtos = products.stream()
                .map(ProductFindResponseDto::fromEntity).toList();

        return ResponseEntity.ok().body(productFindResponseDtos);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductFindResponseDto> findProduct(@PathVariable Long productId, @AuthenticationPrincipal String memberId) {
        Product product = productService.findProduct(productId);
        return ResponseEntity.ok().body(ProductFindResponseDto.fromEntity(product));
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

    //TODO compsoe Patch 만들기
    @PatchMapping("/compose/{productId}")
    public ResponseEntity<Void> createAfterCompose( @AuthenticationPrincipal String memberId, @PathVariable Long productId, ProductSaveRequestDto productSaveRequestDto){
        try {
            //MEMO 일단은 반환하지말고 추후에 프론트에서 필요해지면 반환할 것.
            Long saveId = productService.createAfterCompose( memberId, productId, productSaveRequestDto );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}