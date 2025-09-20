package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class TextController {

    //사용자가 텍스트를 입력할 수 있는 페이지를 보여주는 메서드 (showInputPage)
    @GetMapping("/input") //사용자가 브라우저에서 http://localhost:8080/input 요청을 보냈을 때 실행됨
    public String showInputPage() {
        return "input"; //templates 폴더 안에 있는 input.html 파일을 찾아서 화면에 보여줌
    }

    //사용자가 입력한 텍스트를 받아서 출력 페이지로 전달하는 메서드
    @PostMapping("/output")
    public String handleText(@RequestParam("userText") String userText, Model model) {
        log.info("사용자가 입력한 텍스트: {}", userText);
        model.addAttribute("userText", userText); //   // 사용자가 입력한 텍스트를 모델에 담아서 output.html에서 사용 가능하게 함
        return "output"; //
    }

}
