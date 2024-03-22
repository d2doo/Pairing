package com.ssafy.i10a709be.domain.product.entity;

import com.ssafy.i10a709be.common.entity.BaseEntity;
import com.ssafy.i10a709be.domain.member.entity.Member;
import com.ssafy.i10a709be.domain.product.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Unit extends BaseEntity {

    @Builder
    public Unit(Member member, Product product, Long originalProductId, Category category, Boolean isCombinable, String unitDescription, Integer price, Integer age, Boolean isConfirmed) {
        this.member = member;
        this.product = product;
        this.originalProductId = originalProductId;
        this.category = category;
        this.isCombinable = isCombinable;
        this.unitDescription = unitDescription;
        this.price = price;
        this.age = age;
        this.isConfirmed = isConfirmed;
    }

    @Id
    @GeneratedValue
    private Long unitId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private Long originalProductId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    @ColumnDefault("true")
    private Boolean isCombinable = Boolean.TRUE;

    private String unitDescription;

    private Integer price;

    private Integer age;

    @Setter
    private Boolean isConfirmed;
    
    private String status;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "unit", cascade = CascadeType.PERSIST)
    private final List<Part> parts = new ArrayList<>();

    public void updateProduct(Product product) {
        this.product = product;
    }

    public void updateDetails(boolean isCombinable, String unitDescription, int price, int age, String status) {
        this.isCombinable = isCombinable;
        this.unitDescription = unitDescription;
        this.price = price;
        this.age = age;
        this.status = status;
    }


    @Override
    public String toString() {
        return "Unit{" +
                "unitId=" + unitId +
                ", originalProductId=" + originalProductId +
                ", category=" + category +
                ", isCombinable=" + isCombinable +
                ", unitDescription='" + unitDescription + '\'' +
                ", price=" + price +
                ", age=" + age +
                ", isConfirmed=" + isConfirmed +
                ", status='" + status + '\'' +
                ", parts=" + parts +
                '}';
    }
}
