package com.emgram.kr.dobby.controller;

import com.emgram.kr.dobby.config.auth.PrincipalDetail;
import com.emgram.kr.dobby.dto.login.User;
import com.emgram.kr.dobby.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private final UserService userService;

    @Autowired
    private BCryptPasswordEncoder encoder;


    @PostMapping("/api/v1/users/join")
    public ResponseEntity<String> join(@RequestBody User user){
        userService.join(user);
        return ResponseEntity.ok().body("200");
    }


    @PostMapping("/api/v1/users/login") // 8080/login은 아예 컨트럴러 안 탐. 시큐리티가 알아서 처리하는 것.
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> login(@RequestBody User user){

        String token = userService.login(user);
        return ResponseEntity.ok().body(token);
    }

    @GetMapping("/api/v1/users/user/getAllUsers")
    public ResponseEntity<Map<String, Object>> getAllUsers() {
        try {
            Map<String, Object> map = new HashMap<>();
            List<User> list = userService.getAllUsers();
            map.put("users", list);
            return ResponseEntity.ok(map);
        } catch (Exception e) {
            // Log the exception
            e.printStackTrace();
            // Return a meaningful error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "An internal server error occurred."));
        }
    }


    @PostMapping("/api/v1/users/user/getUser")
    public Map<String, Object> getUser(@RequestBody User user, Authentication authentication){
        Map<String, Object> map=new HashMap<>();
        PrincipalDetail principalDetail =(PrincipalDetail) authentication.getPrincipal();
        String username = principalDetail.getUsername();
        User userFromServer = userService.getUser(username);
        if(encoder.matches(user.getPassword(), userFromServer.getPassword())){
            User newUser=new User();
            newUser.setName(userFromServer.getName());
            newUser.setPassword(user.getPassword());
            newUser.setRoles(userFromServer.getRoles());
            map.put("data", newUser);
            return map;
        }
        return Collections.emptyMap();
    }

    @PostMapping("/api/v1/users/user/update")
    public Map<String, Object> update(@RequestBody User user){
        String tokenRemovingBearer = user.getToken().replace("Bearer ", "");
        user.setToken(tokenRemovingBearer);
        userService.insertToken(user);

        Map<String, Object> map=new HashMap<>();
        int result = userService.update(user);
        if(result==1){
            map.put("success",200);
            map.put("isMatch", userService.comparePwd(user));
            map.put("user", userService.getUser(user.getName()));
        }
      return map;
    }

    @PostMapping("/api/v1/users/user/memo")
    public void insetMemo(@RequestBody User user){
        userService.insertMemo(user);
    }

    @GetMapping("/api/v1/users/user/getUserMemo")
    public ResponseEntity<User> getUserMemo(Authentication authentication){
        PrincipalDetail principalDetail =(PrincipalDetail) authentication.getPrincipal();
        String username = principalDetail.getUsername();
        User user = userService.getUser(username);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/api/v1/users/user/showNotePad")
    public void showNotePad(@RequestBody User user) throws IOException, InterruptedException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec("C:\\windows\\system32\\notepad.exe C:\\Users\\L-JE01\\Desktop\\test\\"+user.getEmployee_no()+".txt");

        String memo = "";
        try {
            FileReader fileReader = new FileReader("C:\\Users\\L-JE01\\Desktop\\test\\"+user.getEmployee_no()+".txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            StringBuilder memoBuilder = new StringBuilder();  // Use StringBuilder to concatenate lines

            while ((memo = bufferedReader.readLine()) != null) {
                memoBuilder.append(memo).append("\n");  // Append each line to the StringBuilder
            }
            memo = memoBuilder.toString();  // Get the final memo content

        } catch (IOException e) {
            e.printStackTrace();
        }

        int exitCode = process.waitFor();
        if (exitCode == 0) {
            try {
                FileReader fileReader = new FileReader("C:\\Users\\L-JE01\\Desktop\\test\\"+user.getEmployee_no()+".txt");
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                StringBuilder memoBuilder = new StringBuilder();  // Use StringBuilder to concatenate lines

                while ((memo = bufferedReader.readLine()) != null) {
                    memoBuilder.append(memo).append("\n");  // Append each line to the StringBuilder
                }
                memo = memoBuilder.toString();  // Get the final memo content

            } catch (IOException e) {
                e.printStackTrace();
            }
            user.setEmployee_no(user.getEmployee_no());
            user.setMemo(memo);
            userService.insertMemo(user);
        }
    }

}
