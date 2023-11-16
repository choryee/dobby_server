package com.emgram.kr.dobby.controller;

import com.emgram.kr.dobby.config.auth.PrincipalDetail;
import com.emgram.kr.dobby.dto.login.User;
import com.emgram.kr.dobby.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    @PostMapping("/login") // 8080/login은 아예 컨트럴러 안 탐. 시큐리티가 알아서 처리하는 것.
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> login(@RequestBody User user){
        System.out.println("UserController 탐.. "+ user);
        System.out.println(user.getName() +" " +user.getPassword());

        String token = userService.login(user);
        return ResponseEntity.ok().body(token);
    }

    @GetMapping("/user/getAllUsers")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        try {
            Map<String, Object> map = new HashMap<>();
            List<User> list = userService.getAllUsers();
            map.put("users", list);
            System.out.println("UserController 탐..getAllUsers >> " + list);
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // Return a meaningful error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "An internal server error occurred."));
        }
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

    @PostMapping("/user/memo")
    public void insetMemo(@RequestBody User user){
        System.out.println("UserController 탐..memo >> "+ user);
        userService.insertMemo(user);
    }

    @GetMapping("/user/getUserMemo")
    public ResponseEntity<User> getUserMemo(Authentication authentication){
        PrincipalDetail principalDetail =(PrincipalDetail) authentication.getPrincipal();
        String username = principalDetail.getUsername();
        User user = userService.getUser(username);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/user/showNotePad")
    public void showNotePad(@RequestBody User user) throws IOException, InterruptedException {
        System.out.println("UserController 탐..showNotePad user.getEmployee_no()>>  "+ user.getEmployee_no());
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("C:\\windows\\system32\\notepad.exe C:\\Users\\L-JE01\\Desktop\\test\\"+user.getEmployee_no()+".txt");

        String memo = "";
        try {
            FileReader fileReader = new FileReader("C:\\Users\\L-JE01\\Desktop\\test\\"+user.getEmployee_no()+".txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            StringBuilder memoBuilder = new StringBuilder();  // Use StringBuilder to concatenate lines

            while ((memo = bufferedReader.readLine()) != null) {
                System.out.println("reader line>> " + memo);
                memoBuilder.append(memo).append("\n");  // Append each line to the StringBuilder
            }
            memo = memoBuilder.toString();  // Get the final memo content

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("memo>> " + memo);

        // Process.waitFor()를 사용하여 메모장 프로세스가 종료될 때까지 대기
        int exitCode = process.waitFor();
        System.out.println("exitCode>>> " + exitCode);

        // 종료 코드가 0이면(정상 종료)에만 메모 저장 작업 수행
        if (exitCode == 0) {
            try {
                FileReader fileReader = new FileReader("C:\\Users\\L-JE01\\Desktop\\test\\"+user.getEmployee_no()+".txt");
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                StringBuilder memoBuilder = new StringBuilder();  // Use StringBuilder to concatenate lines

                while ((memo = bufferedReader.readLine()) != null) {
                    System.out.println("reader line>> " + memo);
                    memoBuilder.append(memo).append("\n");  // Append each line to the StringBuilder
                }
                memo = memoBuilder.toString();  // Get the final memo content

            } catch (IOException e) {
                e.printStackTrace();
            }
            //insert 함.
            user.setEmployee_no(user.getEmployee_no());
            user.setMemo(memo);
            userService.insertMemo(user);
            System.out.println("UserController 탐..insertMemo(user) >>  " + user);
        } else {
            System.out.println("메모장 프로세스가 비정상 종료되었습니다. Exit Code: " + exitCode);
        }

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
