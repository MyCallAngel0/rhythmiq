package com.endava.app.services;

import com.endava.app.domain.Album;
import com.endava.app.domain.Playlist;
import com.endava.app.domain.Song;
import com.endava.app.domain.User;
import com.endava.app.model.AlbumDTO;
import com.endava.app.model.PlaylistDTO;
import com.endava.app.repos.PlaylistRepository;
import com.endava.app.repos.SongRepository;
import com.endava.app.repos.UserRepository;
import com.endava.app.util.exceptions.NotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;
    private final UserRepository userRepository;

    public List<PlaylistDTO> findAll() {
        final List<Playlist> playlists = playlistRepository.findAll(Sort.by("id"));
        return playlists.stream()
                .map(album -> mapToDTO(album, new PlaylistDTO()))
                .toList();
    }

    public PlaylistDTO get(Long id) {
        return playlistRepository.findById(id)
                .map(playlist -> mapToDTO(playlist, new PlaylistDTO()))
                .orElseThrow(() -> new EntityNotFoundException("Playlist not found with id: " + id));
    }

    public Long create(final PlaylistDTO playlistDTO) {
        User user = userRepository.findByName(playlistDTO.getUser());
        if (user == null) {
            log.error("User not found");
            throw new NotFoundException("User not found");
        }
        final Playlist playlist = new Playlist();
        mapToEntity(playlistDTO, playlist, user);
        return playlistRepository.save(playlist).getId();
    }

    public void update(Long id, final PlaylistDTO playlistDTO) {
        final Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Playlist not found with id: " + id));
        playlist.setTitle(playlistDTO.getTitle());
        log.info("Playlist with id {} was successfully updated", id);
        // Обновите поля playlist согласно playlistDetails
        playlistRepository.save(playlist);
    }

    public void delete(Long id) {
        playlistRepository.deleteById(id);
        log.info("Playlist with id {} was successfully deleted", id);
    }

    //TODO : Add songs to playlist fix
    @Transactional
    public void addSongs(Long playlistId, List<Long> songIds) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new EntityNotFoundException("Playlist not found"));

        List<Song> songs = songRepository.findAllById(songIds);
        playlist.getSongs().addAll(songs);

        log.info("Songs added to the playlist with id {}", playlistId);
        playlistRepository.save(playlist);
    }

    private PlaylistDTO mapToDTO(final Playlist playlist, final PlaylistDTO playlistDTO) {
        playlistDTO.setId(playlist.getId());
        playlistDTO.setTitle(playlist.getTitle());
        playlistDTO.setUser(playlist.getUser().getAccountName());
        playlistDTO.setSongs(playlist.getSongs().stream().map(song -> song.getTitle()).toList());
        return playlistDTO;
    }

    private Playlist mapToEntity(final PlaylistDTO playlistDTO, final Playlist playlist, final User artist) {
        playlist.setId(playlistDTO.getId());
        playlist.setTitle(playlistDTO.getTitle());
        playlist.setUser(artist);
        return playlist;
    }
}