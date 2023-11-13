package com.emgram.kr.dobby.controller;

import com.emgram.kr.dobby.config.auth.PrincipalDetail;
import com.emgram.kr.dobby.dto.login.User;
import com.emgram.kr.dobby.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:8081")
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


    @PostMapping("/login") // 8080/login은 아예 컨트럴러 안 탐.
    @CrossOrigin(origins = "http://localhost:8081")
    public ResponseEntity<String> login(@RequestBody User user){
        System.out.println("UserController 탐.. "+ user);
        System.out.println(user.getName() +" " +user.getPassword());

        String token = userService.login(user);
        return ResponseEntity.ok().body(token);
    }

    @GetMapping("/user/getUser")
    public ResponseEntity<User> getUser(Authentication authentication){
        PrincipalDetail principalDetail =(PrincipalDetail) authentication.getPrincipal();
        String username = principalDetail.getUsername();
        User user = userService.getUser(username);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/user/update")
    public Map<String, Object> update(@RequestBody User user){
        System.out.println("UserController 탐../user/update >> "+ user);
        System.out.println(user.getName() +" " +user.getPassword());
        Map<String, Object> map=new HashMap<>();

        user.setName(user.getName());
        userService.endAndModifyPassword(user);

        int result = userService.update(user);
        if(result==1){
            map.put("success",200);
            map.put("isMatch", userService.comparePwd(user));
            map.put("user", userService.getUser(user.getName()));
        }
        System.out.println("pwd update 저장결과>> "+result);
        return map;
    }

    @PostMapping("/user")
    public String user(@RequestBody User user){
        System.out.println("UserController 탐..user >>  "+ user);
        userService.login(user);
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
