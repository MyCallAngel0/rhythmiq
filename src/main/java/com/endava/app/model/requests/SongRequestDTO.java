package com.endava.app.model.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongRequestDTO {

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotNull(message = "ArtistId cannot be blank")
    private Long artistId;

    @NotNull(message = "AlbumId cannot be blank")
    private Long albumId;
}
