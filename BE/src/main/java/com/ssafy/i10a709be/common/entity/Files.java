package com.ssafy.i10a709be.common.entity;


import com.ssafy.i10a709be.domain.product.entity.Unit;
import com.ssafy.i10a709be.domain.product.entity.UnitImages;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Files extends BaseEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long fileId;
    private String source;
    private String name;
    @Column(name = "type")
    private String fileType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "files", cascade = CascadeType.PERSIST)
    private final List<UnitImages> unitImages = new ArrayList<>();
}
