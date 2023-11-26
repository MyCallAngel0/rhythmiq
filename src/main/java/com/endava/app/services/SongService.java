package com.endava.app.services;

import com.endava.app.domain.Album;
import com.endava.app.domain.Song;
import com.endava.app.domain.User;
import com.endava.app.model.SongDTO;
import com.endava.app.repos.AlbumRepository;
import com.endava.app.repos.SongRepository;
import com.endava.app.repos.UserRepository;
import com.endava.app.util.exceptions.NotFoundException;
import com.endava.app.util.exceptions.UnauthorizedException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SongService {

    private final SongRepository songRepository;
    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;

    public List<SongDTO> findAll() {
        final List<Song> songs = songRepository.findAll(Sort.by("id"));
        return songs.stream()
                .map(song -> mapToDTO(song, new SongDTO()))
                .toList();
    }

    public SongDTO get(Long id) {
        return songRepository.findById(id)
                .map(song -> mapToDTO(song, new SongDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final SongDTO songDTO) {
        User artist = userRepository.findByName(songDTO.getArtist());
        if (artist == null) {
            log.error("User not found");
            throw new NotFoundException("User not found");
        }
        Album album = albumRepository.findByTitle(songDTO.getAlbum());
        if (!album.getUser().equals(artist)) {
            log.error("Album with id {} doesn't belong to user {}", album.getId(), artist.getAccountName());
            throw new UnauthorizedException("Album doesn't belong to this user");
        }
        final Song song = new Song();
        mapToEntity(songDTO, song, artist, album);
        return songRepository.save(song).getId();
    }

    @Transactional
    public void update(final Long id, final SongDTO songDTO) {
        final Song song = songRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        song.setTitle(songDTO.getTitle());
        // Обновите другие поля, если они есть
        log.info("Song with id {} was updated", song.getId());
        songRepository.save(song);
    }

    @Transactional
    public void delete(final Long id) {
        songRepository.deleteById(id);
        log.info("Song with id {} was successfully deleted", id);
    }

    private SongDTO mapToDTO(final Song song, final SongDTO songDTO) {
        songDTO.setId(song.getId());
        songDTO.setTitle(song.getTitle());
        songDTO.setAlbum(song.getAlbum().getTitle());
        songDTO.setArtist(song.getArtist().getAccountName());
        return songDTO;
    }

    private Song mapToEntity(final SongDTO songDTO, final Song song, final User artist, final Album album) {
        song.setId(songDTO.getId());
        song.setTitle(songDTO.getTitle());
        song.setArtist(artist);
        song.setAlbum(album);
        return song;
    }
}