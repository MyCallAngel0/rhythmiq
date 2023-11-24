package com.endava.app.model.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class PlaylistRequestDTO {

    @NotBlank(message = "Playlist title cannot be blank")
    private String title;

    @NotNull(message = "UserId cannot be null")
    private Long userId;
}
