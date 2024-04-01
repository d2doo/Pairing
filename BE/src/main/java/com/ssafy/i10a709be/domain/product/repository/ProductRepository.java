package com.ssafy.i10a709be.domain.product.repository;

import com.querydsl.core.BooleanBuilder;
import com.ssafy.i10a709be.domain.product.entity.Product;
import com.ssafy.i10a709be.domain.product.entity.QProduct;
import com.ssafy.i10a709be.domain.product.enums.ProductStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long>, QuerydslPredicateExecutor<Product>{

    @Query("select p from Product p join fetch p.units where p.productId = :productId")
    Optional<Product> findProductAndUnitsByProductId( @Param(value = "productId") Long productId );

    @Query("select p from Product p " +
            "where p.member.memberId = :memberId " +
            "AND p.productId = :productId " +
            "AND (p.status = 'ON_SELL' " +
            "OR p.status = 'PENDING')")
    Optional<Product> deleteProductById(@Param(value = "productId") Long productId, @Param(value = "memberId") String memberId);

    default Page<Product> findProductsByDynamicQuery(Pageable pageable, Long productId, Boolean isCombined, String nickname, String memberId, Long categoryId, String productStatus, Integer startPrice, Integer endPrice, Integer maxAge, String keyword) {
        ProductStatus onsell = ProductStatus.ON_SELL;

        QProduct product = QProduct.product;

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(product.isDeleted.eq(false));

        if (productId != null) {
            builder.and(product.productId.lt(productId));
        }
        if (isCombined != null) {
            if (isCombined) {
                builder.and(product.units.size().goe(2));
            } else {
                builder.and(product.units.size().loe(1));
            }
        }
        if (nickname != null) {
            builder.and(product.member.nickname.eq(nickname));
        }
        if (memberId != null) {
            builder.and(product.member.memberId.eq(memberId));
        }
        if (categoryId != null) {
            builder.and(product.units.any().category.categoryId.eq(categoryId));
        }
        if (productStatus != null) {
            builder.and(product.status.eq(ProductStatus.valueOf(productStatus)));
        } else{
            builder.and(product.status.eq(onsell));
        }
        if (startPrice != null) {
            builder.and(product.totalPrice.goe(startPrice));
        }
        if (endPrice != null) {
            builder.and(product.totalPrice.loe(endPrice));
        }
        if (maxAge != null) {
            builder.and(product.maxAge.loe(maxAge));
        }
        if (keyword != null) {
            builder.and(product.title.contains(keyword));
        }

        return findAll(builder, pageable);
    }
}
