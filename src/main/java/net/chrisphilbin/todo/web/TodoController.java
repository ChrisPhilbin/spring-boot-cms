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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import net.chrisphilbin.todo.entity.Todo;
import net.chrisphilbin.todo.exception.ErrorResponse;
import net.chrisphilbin.todo.service.TodoService;
import net.chrisphilbin.todo.service.UserService;


@AllArgsConstructor
@RestController
@Tag(name = "Todo Controller", description = "Create, retrieve, and edit todo items")
@RequestMapping("/todo")
public class TodoController {

    @Autowired
    TodoService todoService;
    UserService userService;

    @Operation(summary = "Create todo item", description = "Creates a todo item from the provided payload")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successful creation of todo item"),
        @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful submission", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
     })
    @PostMapping
    public ResponseEntity<Todo> saveTodo(@Valid @RequestBody Todo todo, Principal principal) {
        todo.setUser(userService.getUser(principal.getName()));
        return new ResponseEntity<>(todoService.saveTodo(todo), HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieves single todo item", description = "Returns a single todo item by given ID")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Successful retrieval of todo item", content = @Content(schema = @Schema(implementation = Todo.class))),
        @ApiResponse(responseCode = "404", description = "Todo item with specified ID doesn't exist", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Todo.class))))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodo(@PathVariable Long id, Principal principal) {
        Todo todo = todoService.getTodo(id);
        if (todo.getUser() != userService.getUser(principal.getName())) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<Todo>(todo, HttpStatus.OK);
    }

    @Operation(summary = "Retrieves all todo items for authorized user", description = "Returns a list of all todo items for user")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of all todo items for user", content = @Content(schema = @Schema()))
    @GetMapping("/all")
    public ResponseEntity<List<Todo>> getTodos(Principal principal) {
        return new ResponseEntity<>(todoService.getTodos(userService.getUser(principal.getName()).getId()), HttpStatus.OK);
    }

    @Operation(summary = "Delete a todo by ID", description = "Deletes specified todo item by provided ID")
    @DeleteMapping("/{todoId}")
    public ResponseEntity<HttpStatus> deleteTodo(@PathVariable Long todoId, Principal principal) {
        if (todoService.verifyTodoBelongsToUser(todoService.getTodo(todoId), principal)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        todoService.deleteTodo(todoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Update a todo by ID", description = "Updates specified todo item by provided ID")
    @PutMapping("/{todoId}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long todoId, @RequestBody Todo todo, Principal principal) {
        Todo oldTodo = todoService.getTodo(todoId);
        if (oldTodo.getUser().getId() != userService.getUser(principal.getName()).getId()) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<Todo>(todoService.updateTodo(todo, oldTodo), HttpStatus.OK);
    }
    
}
