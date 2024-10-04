package com.challenges.backend.ame.starwars.project.i18n.exception;

import com.challenges.backend.ame.starwars.project.i18n.MessageFactory;
import com.challenges.backend.ame.starwars.project.i18n.Messages;
import org.springframework.http.HttpStatus;

public abstract class ServiceException extends RuntimeException {

    public abstract HttpStatus getHttpStatus();

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Messages message) {
        super(MessageFactory.getMessage(message));
    }

    public ServiceException(Messages message, String... args) {
        super(MessageFactory.getMessage(message, args));
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
