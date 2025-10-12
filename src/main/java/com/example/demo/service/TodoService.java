package com.example.demo.service;

import com.example.demo.model.Todo;
import com.example.demo.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoService {
    private final TodoRepository repo;

    public TodoService(TodoRepository repo) {
        this.repo = repo;
    }

    public Todo createTodo(Todo todo) {
        return repo.save(todo);
    }

    public List<Todo> getAllTodos() {
        return repo.findAll();
    }

    public Optional<Todo> getTodoById(Long id) {
        return repo.findById(id);
    }

    public Todo updateTodo(Long id, Todo todo) {
        todo.setId(id);
        return repo.update(id, todo);
    }

    public void deleteTodo(Long id) {
        repo.delete(id);
    }

    // 추가 기능: 완료/미완료 필터링
    public List<Todo> filterByCompletion(boolean completed) {
        return repo.findAll().stream()
                .filter(todo -> todo.getCompleted() == completed)
                .collect(Collectors.toList());
    }

    // 추가 기능: 우선순위별 정렬
    public List<Todo> sortByPriority() {
        return repo.findAll().stream()
                .sorted((a,b) -> a.getPriority().compareTo(b.getPriority()))
                .collect(Collectors.toList());
    }

    // 추가 기능: 검색
    public List<Todo> searchByTitle(String keyword) {
        return repo.findAll().stream()
                .filter(todo -> todo.getTitle().contains(keyword))
                .collect(Collectors.toList());
    }

    // 추가 기능: 할일 개수 카운트
    public long countTodos() {
        return repo.findAll().size();
    }
}
