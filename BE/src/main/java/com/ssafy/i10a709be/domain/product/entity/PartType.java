package com.ssafy.i10a709be.domain.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class PartType {
    @Builder
    public PartType(Category category, String position) {
        this.category = category;
        this.position = position;
    }

    @Id
    @GeneratedValue
    private Long partTypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private String position;
}
