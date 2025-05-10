package com.havrem.todo.dto.response;

import com.havrem.todo.exception.ErrorDetail;

import java.util.List;

public record ErrorResponse (int code, String message, List<ErrorDetail> errors) {}
