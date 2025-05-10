package com.havrem.todo.exception;

import java.util.List;

public enum ErrorCode {
    TITLE_REQUIRED("titleRequired"),
    TITLE_TOO_LONG("titleTooLong"),
    DESCRIPTION_TOO_LONG("descriptionTooLong"),
    NOT_FOUND("notFound"),
    FORBIDDEN("forbidden"),
    UNAUTHORIZED("unauthorized");

    private final String code;

    ErrorCode(String code) {
        this.code = code;
    }

    public static ErrorCode from(String code) {
        try {
            return ErrorCode.valueOf(code);
        } catch (IllegalArgumentException ex) {
            throw new ApiException(500, "Invalid ErrorCode: " + code, List.of());
        }
    }

    public String code() {
        return code;
    }
}
