package com.endava.app.Controller;

import com.endava.app.Services.PlaylistService;
import com.endava.app.domain.Playlist;
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
@RequestMapping("/api/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping
    public List<Playlist> getAllPlaylists() {
        return playlistService.getAllPlaylists();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Playlist> getPlaylistById(@PathVariable Long id) {
        Playlist playlist = playlistService.getPlaylistById(id);
        return ResponseEntity.ok(playlist);
    }

    @PostMapping
    public ResponseEntity<Playlist> addPlaylist(@RequestBody Playlist playlist) {
        Playlist newPlaylist = playlistService.addPlaylist(playlist);
        return ResponseEntity.ok(newPlaylist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Playlist> updatePlaylist(@PathVariable Long id, @RequestBody Playlist playlistDetails) {
        Playlist updatedPlaylist = playlistService.updatePlaylist(id, playlistDetails);
        return ResponseEntity.ok(updatedPlaylist);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylist(id);
        return ResponseEntity.ok().build();
    }
}