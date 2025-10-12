package com.example.demo.controller;
import com.example.demo.model.Todo;
import com.example.demo.service.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service){
        this.service = service;
    }

    //생성
    @PostMapping
    public Todo create(@RequestBody Todo todo) {
        return service.createTodo(todo);
    }
    
    //전체조회
    @GetMapping
    public List<Todo> getAll() {
        return service.getAllTodos();
    }
    
    //상세조회
    @GetMapping("/{id}")
    public Optional<Todo> getById(@PathVariable Long id) {
        return service.getTodoById(id);
    }

    //업데이트
    @PutMapping("/{id}")
    public Todo update(@PathVariable Long id, @RequestBody Todo todo) {
        return service.updateTodo(id, todo);
    }
    // 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteTodo(id);
    }
    // 추가 기능
    @GetMapping("/filter")
    public List<Todo> filterByCompletion(@RequestParam boolean completed) {
        return service.filterByCompletion(completed);
    }

    @GetMapping("/search")
    public List<Todo> search(@RequestParam String keyword) {
        return service.searchByTitle(keyword);
    }

    @GetMapping("/count")
    public long count() {
        return service.countTodos();
    }





}
