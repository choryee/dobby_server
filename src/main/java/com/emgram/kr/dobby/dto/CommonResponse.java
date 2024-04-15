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
     //`this`는 자바에서 현재 객체를 가리키는 특수한 참조입니다. `this` 키워드를 사용하여 현재 객체의 멤버 변수,
        // 메서드 또는 다른 생성자를 참조할 수 있습니다.
        //위의 코드에서 `this`는 생성자를 호출할 때 사용됩니다. 예를 들어, `CommonResponse` 클래스의 생성자 중 하나가 다른 생성자를 호출할 때
        // `this`를 사용합니다.
        this(0,"성공", result);
    }

    public CommonResponse (String message, T result) {
        this(0, message, result);
    }
}
