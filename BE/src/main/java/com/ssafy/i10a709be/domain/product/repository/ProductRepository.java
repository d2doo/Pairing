package com.ssafy.i10a709be.domain.product.repository;

import com.ssafy.i10a709be.domain.product.entity.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p " +
            "where p.member.memberId = :memberId " +
            "AND p.productId = :productId " +
            "AND (p.status = 'ON_SELL' " +
            "OR p.status = 'PENDING')")
    Optional<Product> deleteProductById(@Param(value = "productId") Long productId, @Param(value = "memberId") String memberId);
}
