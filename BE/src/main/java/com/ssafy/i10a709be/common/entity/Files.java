package com.ssafy.i10a709be.common.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Files {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long fileId;
    private String source;
    private String name;
    private LocalDateTime createAt;
    @Column(name = "type")
    private String fileType;

}
