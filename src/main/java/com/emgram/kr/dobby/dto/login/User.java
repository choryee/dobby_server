package com.emgram.kr.dobby.dto.login;


import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int employee_no;
    private String name;
    private String password;

//    @Enumerated(EnumType.STRING)
//    private RoleType role; // Enum을 쓰면, ADMIN,USER로 형이 강제가 된다.

    //25-1강, 01:14. 위 enum안 쓰고.
    private String role;

    public List<String> getRoleList(){
        if(this.role.length() > 0){
            return Arrays.asList(this.role.split(","));
        }
        return new ArrayList<>();
    }

}

    
