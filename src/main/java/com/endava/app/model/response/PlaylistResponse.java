package com.endava.app.model.response;

import com.endava.app.domain.Song;
import lombok.Data;

import java.util.List;

@Data
public class PlaylistResponse {
    private Long id;

    private String title;

    private String user;

    private List<Song> songs;
}
