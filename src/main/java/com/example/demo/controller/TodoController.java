package com.example.demo.controller;

import com.example.demo.model.Todo;
import com.example.demo.service.TodoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    //생성
    @PostMapping
    public ResponseEntity<Todo> create(@Valid @RequestBody Todo todo) {
        Todo createdTodo = service.createTodo(todo);
        URI location = URI.create("/api/todos/" + createdTodo.getId());
        return ResponseEntity.created(location).body(createdTodo);
    }

    //전체조회
    @GetMapping
    public List<Todo> getAll() {
        return service.getAllTodos();
    }

    //상세조회
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getById(@PathVariable Long id) {
        return service.getTodoById(id)
                .map(ResponseEntity::ok)  //존재할 경우 200 OK
                .orElse(ResponseEntity.notFound().build());  // 존재하지 않을 경우 404 에러 발생
    }

    //업데이트
    @PutMapping("/{id}")
    public ResponseEntity<Todo> update(@Valid @PathVariable Long id, @RequestBody Todo todo) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();  // 존재하지 않으면 404
        }
        Todo updated = service.updateTodo(id, todo);
        return ResponseEntity.ok(updated);
    }


    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();  // 존재하지 않으면 404
        }
        service.deleteTodo(id);
        return ResponseEntity.noContent().build(); // 성공 시 204
    }

    // 추가 기능
    @GetMapping("/filter")
    public List<Todo> filterByCompletion(@RequestParam boolean completed) {
        return service.filterByCompletion(completed);
    }

    //검색
    @GetMapping("/search")
    public List<Todo> search(@RequestParam String keyword) {
        return service.searchByTitle(keyword);
    }

    //총 갯수
    @GetMapping("/count")
    public Map<String, Long> count() {
        return Map.of("count", service.countTodos());

    }

    //정렬
    @GetMapping("/sort")
    public Map<String, Object> sortByPriority() {
        List<Todo> sorted = service.sortByPriority();

        Map<String, Object> response = new HashMap<>();
        response.put("sortType", "priority");
        response.put("count", (long)sorted.size());
        response.put("todos", sorted);
        return response;
    }


}
