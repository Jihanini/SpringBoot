package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HaniController {
    @GetMapping("hello")
    public String errorTest() {
        // 일부러 예외 발생시켜서 Whitelabel Error Page 확인
        throw new RuntimeException("테스트 에러 발생!");


    }
}
