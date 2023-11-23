package com.endava.app.Controller;

import com.endava.app.Services.AlbumService;
import com.endava.app.model.requests.AlbumRequestDTO;
import com.endava.app.model.response.AlbumResponseDTO;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/albums")
@RequiredArgsConstructor
public class AlbumsController {

    private final AlbumService albumService;

    @GetMapping
    public ResponseEntity<List<AlbumResponseDTO>> getAllAlbums() {
        return ResponseEntity.ok(albumService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumResponseDTO> getAlbumById(@PathVariable Long id) {
        return ResponseEntity.ok(albumService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AlbumResponseDTO> createAlbum(@RequestBody AlbumRequestDTO albumDTO) {
        return ResponseEntity.ok(albumService.create(albumDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumResponseDTO> updateAlbum(@PathVariable Long id,
                                                        @RequestBody AlbumRequestDTO albumDTO) {
        return ResponseEntity.ok(albumService.update(id, albumDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAlbum(@PathVariable Long id) {
        albumService.delete(id);
        return ResponseEntity.ok().build();
    }
}

