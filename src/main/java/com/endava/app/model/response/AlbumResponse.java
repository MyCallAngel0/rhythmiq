package com.endava.app.model.response;

import com.endava.app.domain.Song;
import lombok.Data;

import java.util.List;

@Data
public class AlbumResponse {
    private Long id;

    private String title;

    private String artist;

    private List<Song> songs;
}
