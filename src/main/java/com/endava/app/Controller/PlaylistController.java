package com.endava.app.Controller;

import com.endava.app.Services.PlaylistService;
import com.endava.app.model.requests.PlaylistAddSongsBatchRequestDTO;
import com.endava.app.model.requests.PlaylistRequestDTO;
import com.endava.app.model.response.PlaylistResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistResponseDTO> getPlaylistById(@PathVariable Long id) {
        var responseDTO = playlistService.getPlaylistById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<PlaylistResponseDTO>> getAllPlaylists() {
        var playlists = playlistService.getAllPlaylists();
        return ResponseEntity.ok(playlists);
    }

    @PostMapping
    public ResponseEntity<PlaylistResponseDTO> createPlaylist(@RequestBody PlaylistRequestDTO requestDTO) {
        var responseDTO = playlistService.createPlaylist(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistResponseDTO> updatePlaylist(@PathVariable Long id,
                                                              @RequestBody PlaylistRequestDTO requestDTO) {
        var responseDTO = playlistService.updatePlaylist(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/{playlistId}/addSong/{songId}")
    public ResponseEntity<PlaylistResponseDTO> addSongToPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
        var responseDTO = playlistService.addSongToPlaylist(playlistId, songId);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/{playlistId}/addSongs")
    public ResponseEntity<PlaylistResponseDTO> addSongsToPlaylist(@PathVariable Long playlistId, @RequestBody PlaylistAddSongsBatchRequestDTO requestDTO) {
        var responseDTO = playlistService.addSongsToPlaylist(playlistId, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylist(id);
        return ResponseEntity.noContent().build();
    }

}