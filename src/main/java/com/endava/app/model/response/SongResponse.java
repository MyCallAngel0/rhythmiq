package com.endava.app.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongResponse {
    private Long id;

    private String title;

    private String artist;

    private String album;

    private String mp3FilePath;
}
