package com.ssafy.i10a709be.domain.product.service;

import com.ssafy.i10a709be.domain.product.dto.CategoryDto;
import com.ssafy.i10a709be.domain.product.entity.Category;
import com.ssafy.i10a709be.domain.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<String> findMainCategories() {
        return categoryRepository.findDistinctMainCategories();
    }

    @Override
    public Category findByCategoryId(Long categoryId) {
        return categoryRepository.findById( categoryId ).orElseThrow( () -> new IllegalArgumentException("해당하는 카테고리가 없습니다.") );
    }

    @Override
    public List<Category> findSubCategories(String mainCategory) {
        if (mainCategory == null) {
            return categoryRepository.findAll();
        } else {
            return categoryRepository.findAllByMainCategory(mainCategory);
        }
    }
}
