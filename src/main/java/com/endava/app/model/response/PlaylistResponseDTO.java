package com.endava.app.model.response;

import com.endava.app.domain.Song;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Builder
@Setter
@Getter
public class PlaylistResponseDTO {
    private Long id;

    private String title;

    private Long userId;

    private Set<Song> songs;
}
