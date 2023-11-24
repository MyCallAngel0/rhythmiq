package com.endava.app.exceptions.playlists;

public class PlaylistNotFoundException extends RuntimeException {

    public PlaylistNotFoundException() {
        super();
    }

    public PlaylistNotFoundException(final String message) {
        super(message);
    }
}
