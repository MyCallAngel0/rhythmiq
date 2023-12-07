package com.endava.app.services;

import com.endava.app.domain.Album;
import com.endava.app.domain.Song;
import com.endava.app.domain.User;
import com.endava.app.model.request.SongRequest;
import com.endava.app.model.response.SongResponse;
import com.endava.app.repos.AlbumRepository;
import com.endava.app.repos.SongRepository;
import com.endava.app.repos.UserRepository;
import com.endava.app.util.exceptions.song.SongNotFoundException;
import com.endava.app.util.exceptions.user.UnauthorizedException;
import com.endava.app.util.exceptions.user.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class SongService {

    private final SongRepository songRepository;
    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;

    public List<SongResponse> findAll() {
        final List<Song> songs = songRepository.findAll(Sort.by("id"));
        return songs.stream()
                .map(song -> mapToResponse(song, new SongResponse()))
                .toList();
    }

    public SongResponse get(Long id) {
        return songRepository.findById(id)
                .map(song -> mapToResponse(song, new SongResponse()))
                .orElseThrow(SongNotFoundException::new);
    }

    public Long create(final SongRequest songRequest) {
        User artist = userRepository.findByName(songRequest.getArtist());
        if (artist == null) {
            log.error("User not found");
            throw new UserNotFoundException("User not found");
        }
        Album album = albumRepository.findById(songRequest.getAlbum()).orElse(null);
        if (album != null) {
            if (!album.getUser().equals(artist)) {
                log.error("Album with id {} doesn't belong to user {}", album.getId(), artist.getUsername());
                throw new UnauthorizedException("Album doesn't belong to this user");
            }
        }
        final Song song = new Song();
        mapToEntity(songRequest, song, artist, album);
        return songRepository.save(song).getId();
    }

    @Transactional
    public void update(final Long id, final SongRequest songRequest) {
        final Song song = songRepository.findById(id)
                .orElseThrow(SongNotFoundException::new);
        song.setTitle(songRequest.getTitle());
        log.info("Song with id {} was updated", song.getId());
        songRepository.save(song);
    }

    @Transactional
    public void delete(final Long id) {
        songRepository.deleteById(id);
        log.info("Song with id {} was successfully deleted", id);
    }

    private String saveMP3File(SongRequest songRequest) {
        String destination = "/static/assets/songs/" + songRequest.getArtist();
        Path path = FileSystems.getDefault().getPath("src/main/resources" + destination);
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            File filepath = new File("src/main/resources" + destination);
            String fileName = songRequest.getMp3File().getOriginalFilename();
            File file = new File(filepath, fileName);
            Path filePath = file.toPath();
            Files.copy(songRequest.getMp3File().getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return destination + "/" + fileName;
        } catch (IOException e) {
            log.error("Error saving MP3 file: {}", e.getMessage());
            throw new RuntimeException("Error saving MP3 file: " + e.getMessage());
        }
    }

    private SongResponse mapToResponse(final Song song, final SongResponse songResponse) {
        songResponse.setId(song.getId());
        songResponse.setTitle(song.getTitle());
        songResponse.setAlbum((song.getAlbum() != null) ? song.getAlbum().getTitle() : null);
        songResponse.setArtist(song.getArtist().getUsername());
        songResponse.setMp3FilePath(song.getFilepath());
        return songResponse;
    }

    private Song mapToEntity(final SongRequest songRequest, final Song song, final User artist, final Album album) {
        song.setId(songRequest.getId());
        song.setTitle(songRequest.getTitle());
        song.setArtist(artist);
        song.setAlbum(album);
        song.setFilepath(saveMP3File(songRequest));
        return song;
    }
}