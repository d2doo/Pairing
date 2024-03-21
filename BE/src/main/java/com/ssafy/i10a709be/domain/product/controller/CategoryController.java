package com.ssafy.i10a709be.domain.product.controller;

import com.ssafy.i10a709be.domain.product.dto.CategoryDto;
import com.ssafy.i10a709be.domain.product.entity.Category;
import com.ssafy.i10a709be.domain.product.repository.CategoryRepository;
import com.ssafy.i10a709be.domain.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/main")
    public ResponseEntity<List<String>> findMainCategories() {
        return ResponseEntity.ok(categoryService.findMainCategories());
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
