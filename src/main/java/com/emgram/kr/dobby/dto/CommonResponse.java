package com.emgram.kr.dobby.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommonResponse <T> {

    private Integer resultCode;

    private String message;

    private T result;

    public CommonResponse (T result) {
        this(0,"성공", result);
    }

    public CommonResponse (String message, T result) {
        this(0, message, result);
    }
}
