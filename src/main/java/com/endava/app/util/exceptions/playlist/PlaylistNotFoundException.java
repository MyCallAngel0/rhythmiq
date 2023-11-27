package com.endava.app.util.exceptions.playlist;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PlaylistNotFoundException extends RuntimeException {

    public PlaylistNotFoundException() {
        super();
    }

    public PlaylistNotFoundException(final String message) {
        super(message);
    }
}
