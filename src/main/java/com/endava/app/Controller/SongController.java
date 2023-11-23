package com.endava.app.Controller;

import com.endava.app.Services.SongService;
import com.endava.app.model.requests.SongRequestDTO;
import com.endava.app.model.response.SongResponseDTO;
import jakarta.validation.Valid;
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
@RequestMapping("/api/songs")
public class SongController {

    private final SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping
    public ResponseEntity<List<SongResponseDTO>> getAllSongs() {
        var songs = songService.getAllSongs();
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongResponseDTO> getSongById(@PathVariable Long id) {
        var song = songService.getSongById(id);
        return ResponseEntity.ok(song);
    }

    @PostMapping
    public ResponseEntity<SongResponseDTO> addSong(@RequestBody @Valid SongRequestDTO songDTO) {
        var newSong = songService.addSong(songDTO);
        return ResponseEntity.ok(newSong);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SongResponseDTO> updateSong(@PathVariable Long id,
                                                      @RequestBody @Valid SongRequestDTO songDetails) {
        var updatedSong = songService.updateSong(id, songDetails);
        return ResponseEntity.ok(updatedSong);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.ok().build();
    }
}
