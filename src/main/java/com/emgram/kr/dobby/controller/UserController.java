package com.emgram.kr.dobby.controller;

import com.emgram.kr.dobby.dto.login.User;
import com.emgram.kr.dobby.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping("/test")
    public String test(){
        return "test ok..";
    }


    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody User user){
        userService.join(user);
        return ResponseEntity.ok().body("200");
    }


    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> login(@RequestBody User user){
        System.out.println("UserController 탐.. "+ user);
        System.out.println(user.getName() +" " +user.getPassword());

        String token = userService.login(user);
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/user")
    public String user(){
        return "user";
    }

    @PostMapping("/manager")
    public String manager(){
        System.out.println("manager만 접근 가능!!");
        return "manager";
    }

    @PostMapping("/admin")
    public String admin(){
        System.out.println("admin 접근 가능!!");
        return "admin";
    }

}
