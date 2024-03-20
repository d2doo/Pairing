package com.ssafy.i10a709be.domain.product.repository;

import com.ssafy.i10a709be.domain.product.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UnitRepository extends JpaRepository<Unit, Long> {

}