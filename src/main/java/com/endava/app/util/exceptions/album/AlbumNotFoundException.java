package com.endava.app.util.exceptions.album;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AlbumNotFoundException extends RuntimeException {

    public AlbumNotFoundException() {
        super();
    }

    public AlbumNotFoundException(String message) {
        super(message);
    }
}
