package com.havrem.todo.dto.response;

import java.time.LocalDate;

public record TodoResponse(long id, String title, boolean completed, String description, LocalDate due) {}
