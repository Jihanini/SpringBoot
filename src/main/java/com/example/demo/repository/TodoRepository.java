package com.example.demo.repository;

import com.example.demo.model.Todo;
import org.springframework.stereotype.Repository;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class TodoRepository {
    private final ConcurrentMap<Long, Todo> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    // 생성 (Create)
    public Todo save(Todo todo) {
        if (todo.getId() == null) {
            todo.setId(idGenerator.incrementAndGet());
        }
        store.put(todo.getId(), todo);
        return todo;
    }

    // 전체조회 (Read)
    public List<Todo> findAll() {
        return new ArrayList<>(store.values());
    }

    // Read - 상세 조회
    public Optional<Todo> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }
    // Update
    public Todo update(Long id, Todo todo) {
        store.put(id, todo);
        return todo;
    }


    // 삭제(Delete)
    public void delete(Long id) {
        store.remove(id);
    }

}
