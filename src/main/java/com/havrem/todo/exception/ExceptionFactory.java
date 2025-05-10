package com.havrem.todo.exception;

import com.havrem.todo.common.MessageResolver;
import org.springframework.stereotype.Component;

@Component
public class ExceptionFactory {
    private final MessageResolver messageResolver;

    public ExceptionFactory(MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    public NotFoundException notFound(String resource, Object id) {
        return new NotFoundException(messageResolver.resolve(ErrorCode.NOT_FOUND, resource, id));
    }

    public ForbiddenException forbidden() {
        return new ForbiddenException(messageResolver.resolve(ErrorCode.FORBIDDEN));
    }
}
