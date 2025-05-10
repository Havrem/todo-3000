package com.havrem.todo.exception;

import java.util.List;

public class NotFoundException extends ApiException{
    public NotFoundException(String message) {
        super(404, "Resource not found.", List.of(new ErrorDetail(ErrorCode.NOT_FOUND.code(), message, "id", "path")));
    }
}