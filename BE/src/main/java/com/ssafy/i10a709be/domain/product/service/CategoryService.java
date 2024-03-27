package com.ssafy.i10a709be.domain.product.service;

import com.ssafy.i10a709be.domain.product.entity.Category;

import java.util.List;

public interface CategoryService {
    List<String> findMainCategories();

    List<Category> findSubCategories(String mainCategory);
}
