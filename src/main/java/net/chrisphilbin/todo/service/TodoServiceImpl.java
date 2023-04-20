package net.chrisphilbin.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.chrisphilbin.todo.entity.Todo;
import net.chrisphilbin.todo.exception.EntityNotFoundException;
import net.chrisphilbin.todo.repository.TodoRepository;

@AllArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {
    
    private TodoRepository todoRepository;

    @Override
    public Todo getTodo(Long id) {
        Optional<Todo> todo = todoRepository.findById(id);
        return unwrapTodo(todo, id);
    }

    @Override
    public Todo saveTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    @Override
    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);   
    }

    @Override
    public Todo updateTodo(Todo newTodo, Todo oldTodo) {
        oldTodo.setBody(newTodo.getBody());
        oldTodo.setSubject(newTodo.getSubject());
        oldTodo.setIs_complete(newTodo.getIs_complete());
        return todoRepository.save(oldTodo);
    }

    @Override
    public List<Todo> getTodos(Long userId) {
        return todoRepository.findByUserId(userId);
    }

    static Todo unwrapTodo(Optional<Todo> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(id, Todo.class);
    }
}
