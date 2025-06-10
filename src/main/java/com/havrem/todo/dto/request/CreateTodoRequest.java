package com.havrem.todo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateTodoRequest(
        @NotBlank(message = "TITLE_REQUIRED")
        @Size(max = 100, message = "TITLE_TOO_LONG")
        String title,
        @Size(max = 200 , message = "DESCRIPTION_TOO_LONG")
        String description,
        LocalDate due
){}
