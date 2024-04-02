package com.ssafy.i10a709be.domain.product.controller.restcontroller;

import com.ssafy.i10a709be.domain.product.dto.CategoryDto;
import com.ssafy.i10a709be.domain.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryRestController {

    private final CategoryService categoryService;

    @GetMapping("/main")
    public ResponseEntity<List<String>> findMainCategories() {
        return ResponseEntity.ok(categoryService.findMainCategories());
    }

    @GetMapping("/main/{categoryId}")
    public ResponseEntity<CategoryDto> findAllCategoriesById( @PathVariable Long categoryId ){
        return ResponseEntity.ok(CategoryDto.fromEntity(categoryService.findByCategoryId( categoryId )));
    }


    @GetMapping("/sub")
    public ResponseEntity<List<CategoryDto>> findSubCategories(@RequestParam(required = false) String mainCategory) {
        return ResponseEntity.ok(categoryService
                .findSubCategories(mainCategory)
                .stream()
                .map(CategoryDto::fromEntity)
                .toList());
    }
}
