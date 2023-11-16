package com.emgram.kr.dobby.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionManager {

    @ExceptionHandler
    public ResponseEntity<?> appExceptionHandler(AppException e){
        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(e.getErrorCode().name()+" "+ e.getMessage());
    }


}
