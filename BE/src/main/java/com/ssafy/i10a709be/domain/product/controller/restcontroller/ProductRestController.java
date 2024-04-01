package com.ssafy.i10a709be.domain.product.controller.restcontroller;

import com.ssafy.i10a709be.domain.product.dto.ProductFindResponseDto;
import com.ssafy.i10a709be.domain.product.dto.ProductSaveRequestDto;
import com.ssafy.i10a709be.domain.product.entity.Product;
import com.ssafy.i10a709be.domain.product.entity.Unit;
import com.ssafy.i10a709be.domain.product.service.ProductService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Slf4j
public class ProductRestController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Long> saveProduct(@AuthenticationPrincipal String memberId, @RequestBody ProductSaveRequestDto productSaveRequestDto) {
        try {
            log.info(memberId);
            //어쩔수 없다 Product지만 unit id를 반환하자.
            log.info( "dto: "  + productSaveRequestDto.toString() );
            Product product = productService.saveProduct(memberId, productSaveRequestDto);
            return ResponseEntity.ok(product.getUnits().get(0).getUnitId());
        } catch (IllegalArgumentException e) {
            log.info( e.toString() );
            return ResponseEntity.badRequest().build();
        }


    }

    @GetMapping
    public ResponseEntity<List<ProductFindResponseDto>> findAllProduct(
            @RequestParam int size,
            @RequestParam(required = false) String memberId,
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Boolean isCombined,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String productStatus,
            @RequestParam(required = false) Integer startPrice,
            @RequestParam(required = false) Integer endPrice,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) String keyword
    ) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC,"productId"));
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
    @PutMapping("/compose/{unitId}")
    public ResponseEntity<Void> createAfterCompose( @AuthenticationPrincipal String memberId, @PathVariable Long unitId, @RequestBody ProductSaveRequestDto productSaveRequestDto){
//        System.out.println("received:" + productSaveRequestDto.toString() );
        try {
            //MEMO 일단은 반환하지말고 추후에 프론트에서 필요해지면 반환할 것.
            Long saveId = productService.createAfterCompose( memberId, unitId, productSaveRequestDto );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

}