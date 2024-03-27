package com.ssafy.i10a709be.domain.product.entity;

import com.ssafy.i10a709be.common.entity.BaseEntity;
import com.ssafy.i10a709be.common.entity.Files;
import com.ssafy.i10a709be.domain.member.entity.Member;
import com.ssafy.i10a709be.domain.product.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor
//@SQLRestriction("is_deleted = false")
// TODO 합의 거절시 아예 안가져오기에 soft delete가 되버리면 원상복구를 못시킴.
public class Product extends BaseEntity {
    @Builder
    public Product(Member member, String title, ProductStatus status, Integer maxAge, Integer totalPrice) {
        this.member = member;
        this.title = title;
        this.status = status;
        this.maxAge = maxAge;
        this.totalPrice = totalPrice;
    }

    @Id
    @GeneratedValue
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    private Integer maxAge;

    private Integer totalPrice;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product", cascade = CascadeType.PERSIST)
    private List<Unit> units = new ArrayList<>();

    public void updateStatus( ProductStatus status ){

        this.status = status;
    }

    public void modifyTitle(String title) {
        this.title = title;
    }
}
