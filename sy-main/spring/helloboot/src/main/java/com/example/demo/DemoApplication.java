package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

//@SpringBootApplication
//public class DemoApplication {
//
//    public static void main(String[] args) {
//        SpringApplication.run(DemoApplication.class, args);
//    }
//
//}


public class DemoApplication {

//    public static void main(String[] args) {
//
//        MySpringApplication.run(HelloConfig.class, args);
//    }

    // 기본 SpringApplication.run start 코드랑 동일한 형태가 됨
    public static void main(String[] args) {
        SpringApplication.run(HelloConfig.class, args);
    }
}