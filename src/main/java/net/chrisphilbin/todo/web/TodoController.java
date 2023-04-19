package net.chrisphilbin.todo.web;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import net.chrisphilbin.todo.entity.Todo;
import net.chrisphilbin.todo.service.TodoService;
import net.chrisphilbin.todo.service.UserService;


@AllArgsConstructor
@RestController
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    TodoService todoService;
    UserService userService;

    @PostMapping
    public ResponseEntity<Todo> saveTodo(@Valid @RequestBody Todo todo, Principal principal) {
        todo.setUser(userService.getUser(principal.getName()));
        return new ResponseEntity<>(todoService.saveTodo(todo), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodo(@PathVariable Long id, Principal principal) {
        Todo todo = todoService.getTodo(id);
        if (todo.getUser() != userService.getUser(principal.getName())) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<Todo>(todo, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Todo>> getTodos(Principal principal) {
        return new ResponseEntity<>(todoService.getTodos(userService.getUser(principal.getName()).getId()), HttpStatus.OK);
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<HttpStatus> deleteTodo(@PathVariable Long todoId, Principal principal) {
        Todo todo = todoService.getTodo(todoId);
        if (todo.getUser().getId() != userService.getUser(principal.getName()).getId()) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        todoService.deleteTodo(todoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
