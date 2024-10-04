package com.challenges.backend.ame.starwars.project.i18n.exception;

import com.challenges.backend.ame.starwars.project.i18n.MessageFactory;
import com.challenges.backend.ame.starwars.project.i18n.Messages;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends ServiceException {

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

    public NotFoundException(Messages message) {
        super(message);
    }

    public NotFoundException(Messages message, String... args) {
        super(MessageFactory.getMessage(message, args));
    }

    public NotFoundException(String message) {
        super(message);
    }

}
