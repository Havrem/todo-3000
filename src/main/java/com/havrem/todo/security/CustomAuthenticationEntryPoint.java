package com.havrem.todo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.havrem.todo.common.MessageResolver;
import com.havrem.todo.dto.response.ErrorResponse;
import com.havrem.todo.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    public CustomAuthenticationEntryPoint(MessageResolver messageResolver, ObjectMapper objectMapper) {
        this.messageResolver = messageResolver;
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String message = messageResolver.resolve(ErrorCode.UNAUTHORIZED);
        ErrorResponse errorResponse = new ErrorResponse(401, message, List.of());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
