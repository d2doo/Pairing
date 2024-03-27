package com.ssafy.i10a709be.domain.product.entity;

import com.ssafy.i10a709be.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public class Part extends BaseEntity {
    @Builder
    public Part(Unit unit, PartType partType) {
        this.unit = unit;
        this.partType = partType;
    }

    @Id
    @GeneratedValue
    private Long partId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parttype_id")
    private PartType partType;
}