package net.chrisphilbin.todo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import net.chrisphilbin.todo.entity.Todo;

public interface TodoRepository extends CrudRepository<Todo, Long> {
    List<Todo> findByUserId(Long userId);
}
