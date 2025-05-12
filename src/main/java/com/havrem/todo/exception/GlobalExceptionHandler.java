package com.havrem.todo.exception;

import com.havrem.todo.common.MessageResolver;
import com.havrem.todo.dto.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final MessageResolver messageResolver;
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public GlobalExceptionHandler(MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex) {
        logger.warn("Handled exception occurred - status={}, message={}, errors={}", ex.getHttpStatus(), ex.getMessage(), ex.getErrors());
        return ResponseEntity.status(ex.getHttpStatus()).body(new ErrorResponse(ex.getHttpStatus(), ex.getMessage(), ex.getErrors()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        logger.error("Unhandled generic exception occurred", ex);
        return ResponseEntity.status(500).body(new ErrorResponse(500, "Unexpected server error.", List.of()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        List<ErrorDetail> details = ex.getFieldErrors().stream().map(fieldError -> {
            ErrorCode code = ErrorCode.from(fieldError.getDefaultMessage());
            String message = messageResolver.resolve(code);

            return new ErrorDetail(code.name(), message, "field", fieldError.getField());
        }).toList();

        logger.warn("Validation exception occurred - errors={}", details);
        return ResponseEntity.status(400).body(new ErrorResponse(400, "Validation failed due to one or more issues.", details));
    }
}
