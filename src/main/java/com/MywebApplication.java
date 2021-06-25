package com;



import com.example.config.WebSocketConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;




@MapperScan(value = "com.example.dao")
@SpringBootApplication
@EnableCaching

public class MywebApplication {

    public static void main(String[] args) {

        SpringApplication.run(MywebApplication.class, args);

    }


}


