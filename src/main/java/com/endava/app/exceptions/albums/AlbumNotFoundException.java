package com.endava.app.exceptions.albums;

public class AlbumNotFoundException extends RuntimeException {

    public AlbumNotFoundException() {
        super();
    }

    public AlbumNotFoundException(String message) {
        super(message);
    }
}
