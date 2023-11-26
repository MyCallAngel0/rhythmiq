package com.endava.app.model;

import com.endava.app.domain.Song;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlaylistDTO {
    private Long id;

    private String title;

    private String user;

    private List<String> songs;

}
