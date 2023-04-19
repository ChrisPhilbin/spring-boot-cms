package net.chrisphilbin.todo.service;

import java.util.List;

import net.chrisphilbin.todo.entity.Todo;

public interface TodoService {
    Todo getTodo(Long id);
    Todo saveTodo(Todo todo);
    void deleteTodo(Long id);
    List<Todo> getTodos(Long userId);
}
