package com.endava.app.util.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super();
    }

    public UserAlreadyExistsException(final String message) {
        super(message);
    }
}
