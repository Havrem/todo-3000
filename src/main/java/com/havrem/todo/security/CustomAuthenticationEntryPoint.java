package com.havrem.todo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.havrem.todo.common.MessageResolver;
import com.havrem.todo.dto.response.ErrorResponse;
import com.havrem.todo.exception.ErrorCode;
import com.havrem.todo.exception.ErrorDetail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.List;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final MessageResolver messageResolver;
    private final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

    public CustomAuthenticationEntryPoint(MessageResolver messageResolver, ObjectMapper objectMapper) {
        this.messageResolver = messageResolver;
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String message = messageResolver.resolve(ErrorCode.UNAUTHORIZED);
        ErrorResponse errorResponse = new ErrorResponse(401, "Unauthorized attempt", List.of(new ErrorDetail(ErrorCode.UNAUTHORIZED.code(), message, "header", "authorization")));

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), errorResponse);
        logger.warn("Unauthorized access attempt to {} - {}", request.getRequestURI(),authException.getMessage());
    }
}
