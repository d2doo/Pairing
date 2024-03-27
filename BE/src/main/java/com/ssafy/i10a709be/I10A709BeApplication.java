package com.ssafy.i10a709be;

import ch.qos.logback.classic.net.SocketAppender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class I10A709BeApplication {
    //TODO 전체 도메인에 대한 테스트 작성
    public static void main(String[] args) {
        SpringApplication.run(I10A709BeApplication.class, args);
    }

}
