package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cache.annotation.EnableCaching;


@MapperScan("com.example.dao")
@SpringBootApplication
@EnableCaching
public class MywebApplication {

    public static void main(String[] args) {

        SpringApplication.run(MywebApplication.class, args);
    }

}
