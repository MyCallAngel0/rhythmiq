package com.endava.app.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongDTO {
    private Long id;

    @Size(min = 3, max = 30)
    @NotBlank(message = "Song must not be blank!")
    private String title;

    @NotBlank(message = "Artist must not be blank!")
    private String artist;

    private String album;
}
