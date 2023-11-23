package com.endava.app.services;

import com.endava.app.domain.Album;
import com.endava.app.domain.User;
import com.endava.app.model.AlbumDTO;
import com.endava.app.repos.AlbumRepository;
import com.endava.app.repos.UserRepository;
import com.endava.app.util.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final UserRepository userRepository;

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
            return null;
        }
        final Album album = new Album();
        mapToEntity(albumDTO, album, artist);
        return albumRepository.save(album).getId();
    }

    public void update(final Long id, final AlbumDTO albumDTO) {
        final Album album = albumRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        album.setTitle(albumDTO.getTitle());
        albumRepository.save(album);
    }

    public void delete(final Long id) {
        albumRepository.deleteById(id);
    }

    private AlbumDTO mapToDTO(final Album album, final AlbumDTO albumDTO) {
        albumDTO.setId(album.getId());
        albumDTO.setTitle(album.getTitle());
        albumDTO.setArtist(album.getUser().getAccountName());
        return albumDTO;
    }

    private Album mapToEntity(final AlbumDTO albumDTO, final Album album, final User artist) {
        album.setId(albumDTO.getId());
        album.setTitle(albumDTO.getTitle());
        album.setUser(artist);
        return album;
    }
}
