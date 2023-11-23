package com.endava.app.model.response;

import com.endava.app.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SongResponseDTO {

    private String title;

    private User artist;

    private AlbumResponseDTO album;
}
