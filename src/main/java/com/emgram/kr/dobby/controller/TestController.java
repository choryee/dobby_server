package com.emgram.kr.dobby.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PostMapping("/test")
    public String logout(){
        System.out.println("test 호출됨..");
        return "ok";
    }
}
