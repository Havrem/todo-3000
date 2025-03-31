package com.havrem.todo.dtos;

import jakarta.validation.constraints.NotBlank;

public record CreateTodoRequest(@NotBlank String title) {}
