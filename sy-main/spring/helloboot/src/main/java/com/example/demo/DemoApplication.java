package com.example.demo;

import com.example.config.MySpringBootApplication;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

//@SpringBootApplication
//public class DemoApplication {
//
//    public static void main(String[] args) {
//        SpringApplication.run(DemoApplication.class, args);
//    }
//
//}

@MySpringBootApplication
public class DemoApplication {
    private final JdbcTemplate jdbcTemplate;

    public DemoApplication(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    void init() {
        jdbcTemplate.execute("create table if not exists hello(name varchar(50) primary key, count int)");
    }

//    public static void main(String[] args) {
//
//        MySpringApplication.run(HelloConfig.class, args);
//    }

    // 기본 SpringApplication.run start 코드랑 동일한 형태가 됨
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}