package com.endava.app.services;

import com.endava.app.domain.Album;
import com.endava.app.domain.User;
import com.endava.app.model.AlbumDTO;
import com.endava.app.repos.AlbumRepository;
import com.endava.app.repos.SongRepository;
import com.endava.app.repos.UserRepository;
import com.endava.app.util.exceptions.NotFoundException;
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

    public List<AlbumDTO> findAll() {
        final List<Album> albums = albumRepository.findAll(Sort.by("id"));
        return albums.stream()
                .map(album -> mapToDTO(album, new AlbumDTO()))
                .toList();
    }

    public AlbumDTO get(final Long id) {
        return albumRepository.findById(id)
                .map(album -> mapToDTO(album, new AlbumDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final AlbumDTO albumDTO) {
        User artist = userRepository.findByName(albumDTO.getArtist());
        if (artist == null) {
            log.error("User not found");
            throw new NotFoundException("User not found");
        }
        final Album album = new Album();
        mapToEntity(albumDTO, album, artist);
        return albumRepository.save(album).getId();
    }

    @Transactional
    public void update(final Long id, final AlbumDTO albumDTO) {
        final Album album = albumRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        album.setTitle(albumDTO.getTitle());
        log.info("Album with id {} was successfully updated", id);
        albumRepository.save(album);
    }

    @Transactional
    public void delete(final Long id) {
        albumRepository.deleteById(id);
        log.info("Album with id {} was successfully deleted", id);
    }

    private AlbumDTO mapToDTO(final Album album, final AlbumDTO albumDTO) {
        albumDTO.setId(album.getId());
        albumDTO.setTitle(album.getTitle());
        albumDTO.setArtist(album.getUser().getAccountName());
        albumDTO.setSongs(songRepository.findAllByAlbum(album).stream().toList());
        return albumDTO;
    }

    private Album mapToEntity(final AlbumDTO albumDTO, final Album album, final User artist) {
        album.setId(albumDTO.getId());
        album.setTitle(albumDTO.getTitle());
        album.setUser(artist);
        return album;
    }
}
