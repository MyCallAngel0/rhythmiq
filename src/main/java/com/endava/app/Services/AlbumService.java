package com.endava.app.Services;

import com.endava.app.domain.Album;
import com.endava.app.domain.User;
import com.endava.app.exceptions.albums.AlbumNotFoundException;
import com.endava.app.model.requests.AlbumRequestDTO;
import com.endava.app.model.response.AlbumResponseDTO;
import com.endava.app.repos.AlbumRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;

    private final UserService userService;


    public Album getById(Long id) {
        return albumRepository.findById(id)
                .orElseThrow(() -> new AlbumNotFoundException(" Album not found with id: " + id));
    }

    public List<AlbumResponseDTO> findAll() {
        return albumRepository.findAll()
                .stream()
                .map(this::mapToAlbumResponseDTO)
                .collect(Collectors.toList());
    }

    public AlbumResponseDTO findById(Long id) {
        var album = albumRepository.findById(id);
        return album.map(this::mapToAlbumResponseDTO)
                .orElse(null);
    }

    @Transactional
    public AlbumResponseDTO create(AlbumRequestDTO albumDTO) {
        var user = userService.getUserById(albumDTO.getArtistId());
        var album = mapToAlbum(albumDTO, user);
        var savedAlbum = albumRepository.save(album);

        return mapToAlbumResponseDTO(savedAlbum);
    }

    @Transactional
    public AlbumResponseDTO update(Long id, AlbumRequestDTO albumDTO) {
        //TODO: update
        var existingAlbum = albumRepository.findById(id);
        if (existingAlbum.isPresent()) {
            Album updatedAlbum = existingAlbum.get();
            updatedAlbum.setTitle(albumDTO.getTitle());
            Album savedAlbum = albumRepository.save(updatedAlbum);
            return mapToAlbumResponseDTO(savedAlbum);
        }
        return null;
    }

    @Transactional
    public void delete(Long id) {
        albumRepository.deleteById(id);
    }

    public Album mapToAlbum(final AlbumRequestDTO albumRequestDTO, final User user) {
        var album = new Album();
        album.setTitle(albumRequestDTO.getTitle());
        album.setUser(user);
        return album;
    }

    public AlbumResponseDTO mapToAlbumResponseDTO(final Album album) {
        var responseDTO = new AlbumResponseDTO();
        responseDTO.setId(album.getId());
        responseDTO.setTitle(album.getTitle());
        return responseDTO;
    }
}
