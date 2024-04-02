package com.ssafy.i10a709be.common.repository;

import com.ssafy.i10a709be.common.entity.Files;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<Files, Long > {
}
