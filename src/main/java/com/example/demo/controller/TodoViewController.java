package com.example.demo.controller;

import com.example.demo.model.Todo;
import com.example.demo.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
    @RequestMapping("/view/todos")
public class TodoViewController {
    // 메인 페이지 (http://localhost:8080/)
    @GetMapping("/view/todos")
    public String mainPage() {
        return "main"; // templates/main.html 렌더링
    }

    // correction 페이지 (http://localhost:8080/correction)
    @GetMapping("/view/todos/correction")
    public String correctionPage() {
        return "correction"; // templates/correction.html 렌더링
    }

    private final TodoService service;

    public TodoViewController(TodoService service) {
        this.service = service;
    }

    //메인 페이지 (할일 목록)
    @GetMapping("/view/todos")
    public String list(Model model) {
        model.addAttribute("todos", service.getAllTodos());
        return "main"; // main.html 한번 거쳐가라
    }

    //새할일 추가
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("todo", new Todo());
        return "correction";
    }

    // 수정 폼
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Todo todo = service.getTodoById(id).orElseThrow();
        model.addAttribute("todo", todo);
        return "edit";
    }

    //저장 관리 (추가나 수정)
    @PostMapping
    public String save(@ModelAttribute Todo todo) {
        service.createTodo(todo); //id 가 있으면 update 처럼 동작해라
        return "redirect:/view/todos";
    }

    //삭제처리
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.deleteTodo(id);
        return "redirect:/view/todos";
    }


}
