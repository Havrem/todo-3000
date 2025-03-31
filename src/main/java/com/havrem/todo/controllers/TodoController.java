package com.havrem.todo.controllers;

import com.havrem.todo.dtos.CreateTodoRequest;
import com.havrem.todo.dtos.TodoResponse;
import com.havrem.todo.dtos.UpdateTodoRequest;
import com.havrem.todo.models.FirebaseUser;
import com.havrem.todo.services.TodoService;
import com.havrem.todo.models.Todo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todos")
    public List<TodoResponse> getTodos(@AuthenticationPrincipal FirebaseUser user) {
        return todoService.findByUserId(user.uid()).stream().map(TodoResponse::from).toList();
    }

    @PostMapping("/todos")
    public ResponseEntity<TodoResponse> createTodo(@AuthenticationPrincipal FirebaseUser user, @RequestBody CreateTodoRequest request) {
        Todo todo = todoService.createTodo(request, user.uid());
        TodoResponse response = TodoResponse.from(todo);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(todo.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<TodoResponse> updateTodo(@PathVariable Long id, @RequestBody UpdateTodoRequest request) {
        System.out.println(request.completed());
        Todo todo = todoService.updateTodo(request, id);
        TodoResponse response = TodoResponse.from(todo);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<TodoResponse> deleteTodo(@PathVariable Long id) {
        Todo todo = todoService.deleteTodo(id);
        TodoResponse response = TodoResponse.from(todo);
        return ResponseEntity.ok(response);
    }
}
