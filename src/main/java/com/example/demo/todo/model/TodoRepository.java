package com.example.demo.todo.model;
import com.example.demo.todo.model.Todo;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class TodoRepository {
    private final Map<Long, Todo> store = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    // 생성 (Create)1234
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

    // 상세조회 (Read)

    public Optional<Todo> findId(Long id) {
        return Optional.ofNullable(store.get(id));
    }
    // 삭제(Delete
    public void delete(Long id) {
        store.remove(id);
    }

}
