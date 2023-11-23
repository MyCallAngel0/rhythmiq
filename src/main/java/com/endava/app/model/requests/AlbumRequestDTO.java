package com.endava.app.model.requests;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AlbumRequestDTO {

    private String title;

    private Long artistId;
}
