package com.endava.app.controller;

import com.endava.app.model.request.AlbumRequest;
import com.endava.app.services.AlbumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class AlbumController {

    private final AlbumService albumService;

    @Autowired
    public AlbumController(AlbumService userService) {
        this.albumService = userService;
    }

    @GetMapping(path = "albums")
    public String getAllAlbums(final Model model) {
        model.addAttribute("albums", albumService.findAll());
        return "albums/list";
    }

    @GetMapping(path = "album")
    public String getAlbumById(@RequestParam(name="id") Long id, Model model) {
        try {
            log.info("Getting song with id {}", id);
            model.addAttribute("album", albumService.get(id));
            return "albums/songs";
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @PostMapping(path="album/add")
    public ResponseEntity<String> add(@RequestBody final AlbumRequest albumRequest) {
        try {
            log.info("Creating an album");
            Long albumId = albumService.create(albumRequest);
            log.info("Album added with id {}", albumId);
            return ResponseEntity.ok("Album added with id : " + albumId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path= "albums/{album_id}")
    public ResponseEntity<String> update(@PathVariable(name= "album_id") Long albumId, @RequestBody final AlbumRequest albumRequest) {
        try {
            log.info("Updating album with id {}", albumId);
            albumService.update(albumId, albumRequest);
            return ResponseEntity.ok("Album updated");
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path= "albums/delete/{album_id}")
    public String delete(@PathVariable(name= "album_id") Long albumId) {
        try {
            log.info("Deleting album with id {}", albumId);
            albumService.delete(albumId);
            return "redirect:/albums";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "error";
        }
    }
}
