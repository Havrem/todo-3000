package com.havrem.todo.exception;

import java.util.List;

public class ForbiddenException extends ApiException {
    public ForbiddenException(String message) {
        super(403, "You are not allowed to perform this action.", List.of(new ErrorDetail(ErrorCode.FORBIDDEN.code(), message, "id", "path")));
    }
}
