package com.endava.app.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

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

    @NotBlank(message = "You must upload a song")
    private MultipartFile mp3File;
}
