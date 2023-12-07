package com.endava.app.services;

import com.endava.app.domain.Playlist;
import com.endava.app.domain.Song;
import com.endava.app.domain.User;
import com.endava.app.model.request.PlaylistRequest;
import com.endava.app.model.response.PlaylistResponse;
import com.endava.app.repos.PlaylistRepository;
import com.endava.app.repos.SongRepository;
import com.endava.app.repos.UserRepository;
import com.endava.app.util.exceptions.playlist.PlaylistNotFoundException;
import com.endava.app.util.exceptions.playlist.SongAlreadyInPlaylistException;
import com.endava.app.util.exceptions.song.SongNotFoundException;
import com.endava.app.util.exceptions.user.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final SongRepository songRepository;
    private final UserRepository userRepository;

    public List<PlaylistResponse> findAll() {
        final List<Playlist> playlists = playlistRepository.findAll(Sort.by("id"));
        return playlists.stream()
                .map(playlist -> mapToResponse(playlist, new PlaylistResponse()))
                .toList();
    }

    public PlaylistResponse get(Long id) {
        return playlistRepository.findById(id)
                .map(playlist -> mapToResponse(playlist, new PlaylistResponse()))
                .orElseThrow(() -> new PlaylistNotFoundException("Playlist not found with id: " + id));
    }

    public List<PlaylistResponse> getByUsername(String username) {
        final List<Playlist> playlists = playlistRepository.findAllByUser(username);
        return playlists.stream()
                .map(playlist -> mapToResponse(playlist, new PlaylistResponse()))
                .toList();
    }

    public Long create(final PlaylistRequest playlistRequest) {
        log.info(playlistRequest.getUser() + playlistRequest.getTitle());
        User user = userRepository.findByName(playlistRequest.getUser());
        if (user == null) {
            log.error("User not found");
            throw new UserNotFoundException("User not found");
        }
        final Playlist playlist = new Playlist();
        mapToEntity(playlistRequest, playlist, user);
        return playlistRepository.save(playlist).getId();
    }

    public void update(Long id, final PlaylistRequest playlistRequest) {
        final Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new PlaylistNotFoundException("Playlist not found with id: " + id));
        playlist.setTitle(playlistRequest.getTitle());
        log.info("Playlist with id {} was successfully updated", id);
        playlistRepository.save(playlist);
    }

    public void delete(Long id) {
        playlistRepository.deleteById(id);
        log.info("Playlist with id {} was successfully deleted", id);
    }

    @Transactional
    public void addSongs(Long playlistId, List<Long> songIds) {
        var playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new PlaylistNotFoundException("Playlist not found with id: " + playlistId));

        var currentSongs = playlist.getSongs();
        var songsToAdd = songIds.stream()
                .map(songId -> songRepository.findById(songId)
                        .orElseThrow(() -> new SongNotFoundException("Song not found with id: " + songId)))
                .collect(Collectors.toSet());

        var anySongAlreadyInPlaylist = songsToAdd.stream()
                .anyMatch(currentSongs::contains);

        if (anySongAlreadyInPlaylist) {
            throw new SongAlreadyInPlaylistException("One or more songs are already in the playlist");
        }

        playlist.getSongs().addAll(songsToAdd);
        log.info("Songs successfully added in playlist");
        playlistRepository.save(playlist);
    }

    private PlaylistResponse mapToResponse(final Playlist playlist, final PlaylistResponse playlistResponse) {
        playlistResponse.setId(playlist.getId());
        playlistResponse.setTitle(playlist.getTitle());
        playlistResponse.setUser(playlist.getUser().getUsername());
        playlistResponse.setSongs(playlist.getSongs().stream().toList());
        return playlistResponse;
    }

    private Playlist mapToEntity(final PlaylistRequest playlistRequest, final Playlist playlist, final User artist) {
        playlist.setTitle(playlistRequest.getTitle());
        playlist.setUser(artist);
        return playlist;
    }
}