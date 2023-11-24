package com.endava.app.exceptions.playlists;

public class SongAlreadyInPlaylistException extends RuntimeException {
    public SongAlreadyInPlaylistException(String message) {
        super(message);
    }
}

