package com.challenges.backend.ame.starwars.project.i18n.exception;

import com.challenges.backend.ame.starwars.project.i18n.MessageFactory;
import com.challenges.backend.ame.starwars.project.i18n.Messages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ConflictException extends ServiceException {

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }

    public ConflictException(Messages message) {
        super(message);
    }

    public ConflictException(Messages message, String... args) {
        super(MessageFactory.getMessage(message, args));
    }

    public ConflictException(String message) {
        super(message);
    }

}
