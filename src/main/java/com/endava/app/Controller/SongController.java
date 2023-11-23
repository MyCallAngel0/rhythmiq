package com.endava.app.Controller;

import com.endava.app.Services.SongService;
import com.endava.app.domain.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongController {

    private final SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> getSongById(@PathVariable Long id) {
        Song song = songService.getSongById(id);
        return ResponseEntity.ok(song);
    }

    @PostMapping
    public ResponseEntity<Song> addSong(@RequestBody Song song) {
        Song newSong = songService.addSong(song);
        return ResponseEntity.ok(newSong);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Song> updateSong(@PathVariable Long id, @RequestBody Song songDetails) {
        Song updatedSong = songService.updateSong(id, songDetails);
        return ResponseEntity.ok(updatedSong);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.ok().build();
    }
}
