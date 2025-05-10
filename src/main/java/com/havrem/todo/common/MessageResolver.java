package com.havrem.todo.common;

import com.havrem.todo.exception.ErrorCode;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageResolver {
    private final MessageSource messageSource;

    public MessageResolver(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String resolve(ErrorCode code) {
        return messageSource.getMessage(code.name(), null, LocaleContextHolder.getLocale());
    }

    public String resolve(ErrorCode code, Object...args) {
        return messageSource.getMessage(code.name(), args, LocaleContextHolder.getLocale());
    }
}
