package com.ssafy.i10a709be.domain.product.repository;

import com.ssafy.i10a709be.domain.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}
