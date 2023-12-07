package com.endava.app.controller;

import com.endava.app.model.request.PlaylistRequest;
import com.endava.app.services.PlaylistService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Controller
public class PlaylistController {

    private final PlaylistService playlistService;

    @Autowired
    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping(path = "playlists")
    public ResponseEntity<Object> getAllPlaylists() {
        try {
            log.info("Getting all playlists");
            return new ResponseEntity<>(playlistService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{playlist_id}")
    public ResponseEntity<Object> getPlaylistById(@PathVariable(name="playlist_id") Long playlistId) {
        try {
            log.info("Getting song with id {}", playlistId);
            return new ResponseEntity<>(playlistService.get(playlistId), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(path="playlist/add")
    public ResponseEntity<String> add(@RequestBody final PlaylistRequest playlistRequest) {
        try {
            log.info("Creating an playlist");
            Long playlistId = playlistService.create(playlistRequest);
            log.info("Playlist added with id {}", playlistId);
            return ResponseEntity.ok("Playlist added with id : " + playlistId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "playlist/{id}")
    public String getAlbumById(@PathVariable(name="id") Long id, Model model) {
        try {
            log.info("Getting playlist with id {}", id);
            model.addAttribute("playlist", playlistService.get(id));
            return "playlists/songs";
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @PostMapping(path="/add/songs/{playlist_id}")
    public ResponseEntity<String> addSongsToPlaylist(@PathVariable(name="playlist_id") Long playlistId, @RequestBody final PlaylistSongs songIds) {
        try {
            log.info("Adding songs to playlist with id {}", playlistId);
            playlistService.addSongs(playlistId, songIds.getSongIds());
            return ResponseEntity.ok("Songs added");
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path= "/{playlist_id}")
    public ResponseEntity<String> update(@PathVariable(name= "playlist_id") Long playlistId, @RequestBody final PlaylistRequest playlistRequest) {
        try {
            log.info("Updating playlist with id {}", playlistId);
            playlistService.update(playlistId, playlistRequest);
            return ResponseEntity.ok("Playlist updated");
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path= "/{playlist_id}")
    public ResponseEntity<String> delete(@PathVariable(name= "playlist_id") Long playlistId) {
        try {
            log.info("Deleting playlist with id {}", playlistId);
            playlistService.delete(playlistId);
            return ResponseEntity.ok("Playlist deleted");
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Data
    private static class PlaylistSongs {
        private List<Long> songIds;
    }
}