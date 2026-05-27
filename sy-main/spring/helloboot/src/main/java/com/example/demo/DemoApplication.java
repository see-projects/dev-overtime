package com.example.demo;

import com.example.config.MySpringBootApplication;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

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

    @Bean
    ApplicationRunner applicationRunner(Environment environment) {
        return args -> {
            String name = environment.getProperty("my.name");
            System.out.println("name" + name);
        };
    }

    // 기본 SpringApplication.run start 코드랑 동일한 형태가 됨
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}