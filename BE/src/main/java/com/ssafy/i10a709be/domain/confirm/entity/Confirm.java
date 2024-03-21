package com.ssafy.i10a709be.domain.confirm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Confirm {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long confirmId;

}
