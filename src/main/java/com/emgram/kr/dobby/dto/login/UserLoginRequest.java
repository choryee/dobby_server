package com.emgram.kr.dobby.dto.login;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequest {

    private String userName;
    private String password;
}
