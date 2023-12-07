package com.endava.app.controller;


import com.endava.app.services.AuthenticationService;
import com.endava.app.services.PlaylistService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;

@RestController
@Slf4j
@AllArgsConstructor
public class APIController {
    private final AuthenticationService authService;
    private final PlaylistService playlistService;

    @GetMapping("api/token")
    public String getUsernameFromToken() {
        return authService.getUserCredentials();
    }

    @GetMapping(path = "api/playlists/{username}")
    public ResponseEntity<Object> getPlaylistByUsername(@PathVariable(name = "username") String username) {
        try {
            log.info("Getting playlists from user {}", username);
            return new ResponseEntity<>(playlistService.getByUsername(username), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{filepath}")
    public ResponseEntity<Resource> getMp3File(@PathVariable(name = "filepath") String filepath) throws IOException {
        Resource mp3Resource = new ClassPathResource(filepath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("audio/mp3"));
        headers.setContentLength(mp3Resource.contentLength());
        headers.setContentDispositionFormData("attachment", mp3Resource.getFilename());

        return new ResponseEntity<>(new InputStreamResource(mp3Resource.getInputStream()), headers, HttpStatus.OK);
    }

}
