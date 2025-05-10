package com.havrem.todo.exception;

import java.util.List;

public class ApiException extends RuntimeException {
    private final int httpStatus;
    private final List<ErrorDetail> errors;


    public ApiException(int httpStatus, String message, List<ErrorDetail> errors) {
        super(message);
        this.httpStatus = httpStatus;
        this.errors = errors;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public List<ErrorDetail> getErrors() {
        return errors;
    }
}
