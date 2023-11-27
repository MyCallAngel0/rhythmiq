package com.endava.app.model;

import com.endava.app.domain.Song;
import com.endava.app.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AlbumDTO {
    private Long id;
    @NotBlank(message = "Album must not be blank!")
    @Size(min = 3, max = 30)
    private String title;
    @NotBlank(message = "Artist must not be blank!")
    private String artist;
    private List<String> songs;
}
