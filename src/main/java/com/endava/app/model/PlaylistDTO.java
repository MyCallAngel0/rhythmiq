package com.endava.app.model;

import com.endava.app.domain.Song;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlaylistDTO {
    private Long id;

    @NotBlank(message = "Title must not be blank!")
    @Size(max = 30)
    private String title;

    @NotBlank(message = "User must not be blank!")
    private String user;

    private List<String> songs;

}
