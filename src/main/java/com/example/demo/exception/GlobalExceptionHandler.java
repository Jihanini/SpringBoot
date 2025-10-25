package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //MAP.op() 빠르고 간단한 JSON 응답
    //404 관련 에러 처리
    @ExceptionHandler(TodoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleTodoNotFoundException(TodoNotFoundException ex) {
        return Map.of(
                "error", "Not Found",
                "message", ex.getMessage()
        );
    }

    //기타 예외 처리
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleException(Exception ex) {
        return Map.of(
                "error", "Internal Server Error",
                "message", ex.getMessage()
        );
    }
}

