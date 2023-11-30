package com.emgram.kr.dobby;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EncoderTest{

    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void encryptTest(){
        String plainPassword = "111";
        String encodedPassword = bCryptPasswordEncoder.encode(plainPassword);

        System.out.println("=================== EncryptPassword ====================");
        System.out.println(encodedPassword);
        System.out.println("=================== EncryptPassword ====================");

        if(bCryptPasswordEncoder.matches("111", "$2a$10$ghjs1c8H9P1uGf2FUaLO.es4PbL4y3aMQBl62np/nLVQkRGM7ktPm")){ // null
            System.out.println("비번일치함.");
        }else {
            System.out.println("비번일치 안 함.");
        }

        assertAll(
                () -> assertNotEquals(plainPassword, encodedPassword),
                () -> assertTrue(bCryptPasswordEncoder.matches(plainPassword, encodedPassword))
        );
    }
}