package com.ssafy.i10a709be.domain.product.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.ssafy.i10a709be.domain.product.entity.Category;
import com.ssafy.i10a709be.domain.product.entity.Product;
import com.ssafy.i10a709be.domain.product.entity.QProduct;
import com.ssafy.i10a709be.domain.product.entity.QUnit;
import com.ssafy.i10a709be.domain.product.enums.ProductStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p join fetch p.units where p.productId = :productId")
    Product findProductByProductIdAndUnits( @Param(value = "productId") Long productId );

    @Query("select p from Product p " +
            "where p.member.memberId = :memberId " +
            "AND p.productId = :productId " +
            "AND (p.status = 'ON_SELL' " +
            "OR p.status = 'PENDING')")
    Optional<Product> deleteProductById(@Param(value = "productId") Long productId, @Param(value = "memberId") String memberId);

    default List<Product> findProductsByDynamicQuery(String nickname, Long categoryId, String productStatus, Integer startPrice, Integer endPrice, Integer maxAge, String keyword) {
        QProduct product = QProduct.product;

        BooleanBuilder builder = new BooleanBuilder();

        if (nickname != null) {
            builder.and(product.member.nickname.eq(nickname));
        }
        if (categoryId != null) {
            builder.and(product.units.any().category.categoryId.eq(categoryId));
        }
        if (productStatus != null) {
            builder.and(product.status.eq(ProductStatus.valueOf(productStatus)));
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

        return (List<Product>) findAll(builder);
    }
}
