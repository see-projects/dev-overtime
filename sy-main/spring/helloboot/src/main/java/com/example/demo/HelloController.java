package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

//@RestController
//public class HelloController {
//    @GetMapping("/hello")
//    public String hello(String name) {
//        return "Hello " + name;
//    }
//}

@RequestMapping
public class HelloController {
    private HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello(String name) {

        return helloService.sayHello(Objects.requireNonNull(name));
    }
}
