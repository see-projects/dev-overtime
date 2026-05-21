package com.example.demo;

import com.example.config.MySpringBootApplication;
import org.springframework.boot.SpringApplication;

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

//    public static void main(String[] args) {
//
//        MySpringApplication.run(HelloConfig.class, args);
//    }

    // 기본 SpringApplication.run start 코드랑 동일한 형태가 됨
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}