package com.endava.app.model;

import com.endava.app.domain.Song;
import com.endava.app.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AlbumDTO {
    private Long id;
    private String title;
    private String artist;
    private List<String> songs;
}
