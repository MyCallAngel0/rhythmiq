package com.endava.app.controller;

import com.endava.app.model.SongDTO;
import com.endava.app.services.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/songs")
public class SongController {

    private final SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllSongs() {
        try {
            log.info("Getting all songs");
            return new ResponseEntity<>(songService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{song_id}")
    public ResponseEntity<Object> getSongById(@PathVariable(name="song_id") Long songId) {
        try {
            log.info("Getting song with id {}", songId);
            return new ResponseEntity<>(songService.get(songId), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody final SongDTO songDTO) {
        try {
            log.info("Adding a song");
            Long songId = songService.create(songDTO);
            log.info("Song added with id {}", songId);
            return ResponseEntity.ok("Song added with id : " + songId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path= "/{song_id}")
    public ResponseEntity<String> update(@PathVariable(name= "song_id") Long songId, @RequestBody final SongDTO songDTO) {
        try {
            log.info("Updating song with id {}", songId);
            songService.update(songId, songDTO);
            return ResponseEntity.ok("Song updated");
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path= "/{song_id}")
    public ResponseEntity<String> delete(@PathVariable(name= "song_id") Long songId) {
        try {
            log.info("Deleting song with id {}", songId);
            songService.delete(songId);
            return ResponseEntity.ok("Song deleted");
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}