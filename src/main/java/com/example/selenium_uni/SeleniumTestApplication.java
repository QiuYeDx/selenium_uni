package com.example.selenium_uni;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.selenium_uni.dao")
public class SeleniumTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeleniumTestApplication.class, args);
    }
}
