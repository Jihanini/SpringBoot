package com.example.demo.controller;

import com.example.demo.exception.TodoNotFoundException;
import com.example.demo.model.Todo;
import com.example.demo.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/view/todos")
public class TodoViewController {

    private final TodoService service;

    public TodoViewController(TodoService service) {
        this.service = service;
    }

    //메인 페이지 (할일 목록)
    @GetMapping
    public String list(Model model) {
        model.addAttribute("todos", service.getAllTodos());
        return "main-improved";
    }

    // correction 페이지
    @GetMapping("/correction")
    public String correctionPage() {
        return "correction";
    }

    //새할일 추가
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("todo", new Todo());
        return "form-improved";
    }

    // 수정 폼
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Todo todo = service.getTodoById(id)
                .orElseThrow(() -> new TodoNotFoundException(id)); // 의미 있는 예외 발생
        model.addAttribute("todo", todo);
        return "form-improved";
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
