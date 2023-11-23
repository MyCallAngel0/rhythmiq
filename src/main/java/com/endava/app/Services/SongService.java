package com.endava.app.Services;

import com.endava.app.domain.Album;
import com.endava.app.domain.Song;
import com.endava.app.domain.User;
import com.endava.app.exceptions.songs.SongNotFoundException;
import com.endava.app.model.requests.SongRequestDTO;
import com.endava.app.model.response.SongResponseDTO;
import com.endava.app.repos.SongRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;

    private final AlbumService albumService;

    private final UserService userService;

    public List<SongResponseDTO> getAllSongs() {
        var songs = songRepository.findAll(Sort.by("id"));
        log.info("Getting all songs");
        return songs.stream()
                .map(user -> mapToSongResponseDTO(user))
                .toList();
    }

    public SongResponseDTO getSongById(Long id) {
        log.info("Getting song with id: {}", id);
        return songRepository.findById(id)
                .map(song -> mapToSongResponseDTO(song))
                .orElseThrow(() -> new RuntimeException("Song not found with id: " + id));
    }

    @Transactional
    public SongResponseDTO addSong(SongRequestDTO songDTO) {
        log.info("Adding song with title: {}", songDTO.getTitle());
        var album = albumService.getById(songDTO.getAlbumId());
        var user = userService.getUserById(songDTO.getArtistId());
        var song = mapToSong(songDTO, album, user);

        songRepository.save(song);
        log.info("Song with title: {} added successfully", songDTO.getTitle());

        return mapToSongResponseDTO(song);
    }

    @Transactional
    public SongResponseDTO updateSong(Long id, SongRequestDTO songDetails) {
        log.info("Updating song with id: {}", id);
        var song = songRepository.findById(id)
                .orElseThrow(() -> new SongNotFoundException("Song not found with id: " + id));

        if (songDetails.getTitle() != null) {
            song.setTitle(songDetails.getTitle());
        }

        if (songDetails.getArtistId() != null) {
            var user = userService.getUserById(songDetails.getArtistId());
            song.setArtist(user);
        }

        if (songDetails.getAlbumId() != null) {
            var album = albumService.getById(songDetails.getAlbumId());
            song.setAlbum(album);
        }

        songRepository.save(song);
        log.info("Song with id: {} updated successfully", id);

        return mapToSongResponseDTO(song);
    }

    @Transactional
    public void deleteSong(Long id) {
        log.info("Deleting song with id: {}", id);
        songRepository.findById(id)
                .orElseThrow(() -> new SongNotFoundException("Song not found with id: " + id));
        songRepository.deleteById(id);
    }

    private Song mapToSong(final SongRequestDTO songDTO, final Album album, final User artist) {
        return Song.builder()
                .title(songDTO.getTitle())
                .album(album)
                .artist(artist)
                .build();
    }

    private SongResponseDTO mapToSongResponseDTO(final Song song) {
        return SongResponseDTO.builder()
                .title(song.getTitle())
                .artist(song.getArtist())
                .album(albumService.mapToAlbumResponseDTO(song.getAlbum()))
                .build();

    }
}
