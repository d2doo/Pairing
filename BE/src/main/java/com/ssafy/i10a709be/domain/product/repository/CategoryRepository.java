package com.ssafy.i10a709be.domain.product.repository;

import com.ssafy.i10a709be.domain.product.dto.CategoryDto;
import com.ssafy.i10a709be.domain.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT DISTINCT c.mainCategory FROM Category c")
    List<String> findDistinctMainCategories();

    List<Category> findAllByMainCategory(String mainCategory);
}
