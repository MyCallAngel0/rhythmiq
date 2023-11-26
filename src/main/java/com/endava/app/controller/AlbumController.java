package com.endava.app.controller;

import com.endava.app.model.AlbumDTO;
import com.endava.app.services.AlbumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(path="/albums")
public class AlbumController {

    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService userService) {
        this.albumService = userService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllAlbums() {
        try {
            log.info("Getting all albums");
            return new ResponseEntity<>(albumService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{album_id}")
    public ResponseEntity<Object> getAlbumById(@PathVariable(name="album_id") Long albumId) {
        try {
            log.info("Getting song with id {}", albumId);
            return new ResponseEntity<>(albumService.get(albumId), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path="/add")
    public ResponseEntity<String> add(@RequestBody final AlbumDTO albumDTO) {
        try {
            log.info("Creating an album");
            Long albumId = albumService.create(albumDTO);
            log.info("Album added with id {}", albumId);
            return ResponseEntity.ok("Album added with id : " + albumId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path= "/{album_id}")
    public ResponseEntity<String> update(@PathVariable(name= "album_id") Long albumId, @RequestBody final AlbumDTO albumDTO) {
        try {
            log.info("Updating album with id {}", albumId);
            albumService.update(albumId, albumDTO);
            return ResponseEntity.ok("Album updated");
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path= "/{album_id}")
    public ResponseEntity<String> delete(@PathVariable(name= "album_id") Long albumId) {
        try {
            log.info("Deleting album with id {}", albumId);
            albumService.delete(albumId);
            return ResponseEntity.ok("Album deleted");
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
