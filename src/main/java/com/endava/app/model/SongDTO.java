package com.endava.app.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongDTO {
    private Long id;

    @Size(min = 3, max = 50)
    private String title;

    private String artist;

    private String album;
}
