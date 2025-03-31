package com.havrem.todo.dtos;

import com.havrem.todo.models.Todo;

public record TodoResponse(long id, String title, boolean completed) {
    public static TodoResponse from(Todo todo) {
        return new TodoResponse(todo.getId(), todo.getTitle(), todo.isCompleted());
    }
}
