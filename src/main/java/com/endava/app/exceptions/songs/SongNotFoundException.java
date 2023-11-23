package com.endava.app.exceptions.songs;

public class SongNotFoundException extends RuntimeException {

    public SongNotFoundException() {
        super();
    }

    public SongNotFoundException(final String message) {
        super(message);
    }
}
