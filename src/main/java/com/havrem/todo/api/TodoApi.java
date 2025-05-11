package com.havrem.todo.api;

import com.havrem.todo.dto.request.CreateTodoRequest;
import com.havrem.todo.dto.request.UpdateTodoRequest;
import com.havrem.todo.dto.response.ErrorResponse;
import com.havrem.todo.dto.response.TodoResponse;
import com.havrem.todo.model.FirebaseUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@SecurityScheme(
        name = "firebase_auth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "Use Firebase-issued JWT. Add it as a 'Bearer <token>' in the Authorization header"
)
@SecurityRequirement(name = "firebase_auth")
public interface TodoApi {
    @Operation(summary = "Get todos", description = "Retrieve all todos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TodoResponse.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - missing or invalid Firebase token", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/todos")
    ResponseEntity<List<TodoResponse>> getTodos(@AuthenticationPrincipal FirebaseUser user);

    @Operation(summary = "Create todo", description = "Create a new todo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Todo successfully created", content = @Content(schema = @Schema(implementation = TodoResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - missing or invalid Firebase token", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden - insufficient permissions", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/todos")
    ResponseEntity<TodoResponse> createTodo(@AuthenticationPrincipal FirebaseUser user, @Valid @RequestBody CreateTodoRequest request);

    @Operation(summary = "Update todo", description = "Update an existing todo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todo successfully updated", content = @Content(schema = @Schema(implementation = TodoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request - validation failed", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - missing or invalid Firebase token", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden - insufficient permissions", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Todo not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/todos/{id}")
    ResponseEntity<TodoResponse> updateTodo(@AuthenticationPrincipal FirebaseUser user, @PathVariable Long id, @Valid @RequestBody UpdateTodoRequest request);

    @Operation(summary = "Delete todo", description = "Delete an existing todo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Todo successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid request - validation failed", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized - missing or invalid Firebase token", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden - insufficient permissions", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Todo not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/todos/{id}")
    ResponseEntity<Void> deleteTodo(@AuthenticationPrincipal FirebaseUser user, @PathVariable Long id);
}
