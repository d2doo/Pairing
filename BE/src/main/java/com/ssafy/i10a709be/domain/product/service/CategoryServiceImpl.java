package com.ssafy.i10a709be.domain.product.service;

import com.ssafy.i10a709be.domain.product.entity.Category;
import com.ssafy.i10a709be.domain.product.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

//    @Override
//    public List<Category> findMainCategoryList(String mainCategoryName) {
//
//    }
}
