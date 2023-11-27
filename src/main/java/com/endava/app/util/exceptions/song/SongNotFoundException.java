package com.endava.app.util.exceptions.song;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SongNotFoundException extends RuntimeException {

    public SongNotFoundException() {
        super();
    }

    public SongNotFoundException(final String message) {
        super(message);
    }
}
