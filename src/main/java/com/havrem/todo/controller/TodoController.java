package com.havrem.todo.controller;

import com.havrem.todo.api.TodoApi;
import com.havrem.todo.dto.request.CreateTodoRequest;
import com.havrem.todo.dto.response.TodoResponse;
import com.havrem.todo.dto.request.UpdateTodoRequest;
import com.havrem.todo.model.FirebaseUser;
import com.havrem.todo.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class TodoController implements TodoApi {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todos")
    public ResponseEntity<List<TodoResponse>> getTodos(@AuthenticationPrincipal FirebaseUser user) {
        List<TodoResponse> todos = todoService.findByUserId(user);
        return ResponseEntity.ok(todos);
    }

    @PostMapping("/todos")
    public ResponseEntity<TodoResponse> createTodo(@AuthenticationPrincipal FirebaseUser user, @Valid @RequestBody CreateTodoRequest request) {
        TodoResponse response = todoService.createTodo(request, user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<TodoResponse> updateTodo(@AuthenticationPrincipal FirebaseUser user, @PathVariable Long id, @Valid @RequestBody UpdateTodoRequest request) {
        TodoResponse response = todoService.updateTodo(user, request, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/todos/{id}")
    public ResponseEntity<Void> deleteTodo(@AuthenticationPrincipal FirebaseUser user, @PathVariable Long id) {
        todoService.deleteTodo(user, id);
        return ResponseEntity.noContent().build();
    }
}
