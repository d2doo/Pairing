package com.ssafy.i10a709be.domain.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Category {
    @Builder
    public Category(String mainCategory, String subCategory) {
        this.mainCategory = mainCategory;
        this.subCategory = subCategory;
    }

    @Id
    @GeneratedValue
    private Long categoryId;

    private String mainCategory;
    private String subCategory;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category", cascade = CascadeType.PERSIST)
    private List<PartType> partTypes;
}
