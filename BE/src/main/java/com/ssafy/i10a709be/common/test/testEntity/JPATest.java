package com.ssafy.i10a709be.common.test.testEntity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data // 원래 쓰면 안돼요.
public class JPATest {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long test_id;

}
