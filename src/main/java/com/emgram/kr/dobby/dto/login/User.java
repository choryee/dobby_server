package com.emgram.kr.dobby.dto.login;


import lombok.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String employee_no;
    private String name;
    private String employee_name;
    private String password;
    private String roles;
    private String token;
    private String memo;
    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

}

    
