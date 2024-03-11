package com.ssafy.i10a709be.common.test;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class YmlChecker {

    @Value("${ymlchecker}")
    private String temp;
    @PostConstruct
    public void YmlChecking(){
        log.info("--------------------- ymlchecker --------------------------");
        log.info("profile: " + temp );
    }
}
