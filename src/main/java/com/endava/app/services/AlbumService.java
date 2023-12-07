package com.endava.app.services;

import com.endava.app.domain.Album;
import com.endava.app.domain.User;
import com.endava.app.model.request.AlbumRequest;
import com.endava.app.model.response.AlbumResponse;
import com.endava.app.repos.AlbumRepository;
import com.endava.app.repos.SongRepository;
import com.endava.app.repos.UserRepository;
import com.endava.app.util.exceptions.album.AlbumNotFoundException;
import com.endava.app.util.exceptions.user.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;

    public List<AlbumResponse> findAll() {
        final List<Album> albums = albumRepository.findAll(Sort.by("id"));
        return albums.stream()
                .map(album -> mapToResponse(album, new AlbumResponse()))
                .toList();
    }

    public AlbumResponse get(final Long id) {
        return albumRepository.findById(id)
                .map(album -> mapToResponse(album, new AlbumResponse()))
                .orElseThrow(AlbumNotFoundException::new);
    }

    public Long create(final AlbumRequest albumRequest) {
        User artist = userRepository.findByName(albumRequest.getArtist());
        if (artist == null) {
            log.error("User not found");
            throw new UserNotFoundException("User not found");
        }
        final Album album = new Album();
        mapToEntity(albumRequest, album, artist);
        return albumRepository.save(album).getId();
    }

    @Transactional
    public void update(final Long id, final AlbumRequest albumRequest) {
        final Album album = albumRepository.findById(id)
                .orElseThrow(AlbumNotFoundException::new);
        album.setTitle(albumRequest.getTitle());
        log.info("Album with id {} was successfully updated", id);
        albumRepository.save(album);
    }

    @Transactional
    public void delete(final Long id) {
        albumRepository.deleteById(id);
        log.info("Album with id {} was successfully deleted", id);
    }

    private AlbumResponse mapToResponse(final Album album, final AlbumResponse albumResponse) {
        albumResponse.setId(album.getId());
        albumResponse.setTitle(album.getTitle());
        albumResponse.setArtist(album.getUser().getUsername());
        albumResponse.setSongs(songRepository.findAllByAlbum(album).stream().toList());
        return albumResponse;
    }

    private Album mapToEntity(final AlbumRequest albumRequest, final Album album, final User artist) {
        album.setId(albumRequest.getId());
        album.setTitle(albumRequest.getTitle());
        album.setUser(artist);
        return album;
    }
}
