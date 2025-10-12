package com.example.demo.exception;
public class TodoNotFoundException extends RuntimeException {
    public TodoNotFoundException(Long id) {
        super("Todo with ID " + id + " not found");
    }
}
