package com.ssafy.i10a709be.domain.product.repository;

import com.ssafy.i10a709be.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
