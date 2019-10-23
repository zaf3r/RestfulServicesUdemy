package com.zafer.RestfulServicesUdemy;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    public String helloWorld(){
        return "hello world";
    }

    @GetMapping("/hello-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Taichu and Tin are cute ~");
    }

    @GetMapping("/hello/{name}")
    public HelloWorldBean helloWorldBean(@PathVariable String name){
        return new HelloWorldBean(String.format("Hello world, %s",name));
    }

}
