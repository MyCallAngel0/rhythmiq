package com.endava.app.model.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlaylistAddSongsBatchRequestDTO {
    private List<Long> songIds;
}
