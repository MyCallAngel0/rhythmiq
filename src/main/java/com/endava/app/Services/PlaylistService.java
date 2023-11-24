package com.endava.app.Services;

import com.endava.app.domain.Playlist;
import com.endava.app.domain.User;
import com.endava.app.exceptions.UserNotFoundException;
import com.endava.app.exceptions.playlists.PlaylistNotFoundException;
import com.endava.app.exceptions.playlists.SongAlreadyInPlaylistException;
import com.endava.app.exceptions.songs.SongNotFoundException;
import com.endava.app.model.requests.PlaylistAddSongsBatchRequestDTO;
import com.endava.app.model.requests.PlaylistRequestDTO;
import com.endava.app.model.response.PlaylistResponseDTO;
import com.endava.app.repos.PlaylistRepository;
import com.endava.app.repos.SongRepository;
import com.endava.app.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    private final UserRepository userRepository;

    private final SongRepository songRepository;

    public PlaylistResponseDTO getPlaylistById(Long id) {
        var playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new PlaylistNotFoundException("Playlist not found with id: " + id));
        return mapToResponseDTO(playlist);
    }

    public List<PlaylistResponseDTO> getAllPlaylists() {
        log.info("Getting all playlists");
        return playlistRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PlaylistResponseDTO createPlaylist(PlaylistRequestDTO requestDTO) {
        log.info("Creating Playlist");
        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + requestDTO.getUserId()));

        var playlist = mapDTOtoEntity(requestDTO, user);

        var savedPlaylist = playlistRepository.save(playlist);
        log.info("Playlist Created");
        return mapToResponseDTO(savedPlaylist);
    }

    @Transactional
    public PlaylistResponseDTO updatePlaylist(Long id, PlaylistRequestDTO requestDTO) {
        var playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new PlaylistNotFoundException("Playlist not found with id: " + id));

        if (requestDTO.getTitle() != null) {
            playlist.setTitle(requestDTO.getTitle());
        }

        if (requestDTO.getUserId() != null) {
            var user = userRepository.findById(requestDTO.getUserId())
                    .orElseThrow(() -> new UserNotFoundException("User not found with id: " + requestDTO.getUserId()));
            playlist.setUser(user);
        }

        var updatedPlaylist = playlistRepository.save(playlist);
        return mapToResponseDTO(updatedPlaylist);
    }

    @Transactional
    public PlaylistResponseDTO addSongToPlaylist(Long playlistId, Long songId) {
        var playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new PlaylistNotFoundException("Playlist not found with id: " + playlistId));

        var song = songRepository.findById(songId)
                .orElseThrow(() -> new SongNotFoundException("Song not found with id: " + songId));

        if (playlist.getSongs().contains(song)) {
            throw new SongAlreadyInPlaylistException("Song with id " + songId + " is already in the playlist");
        }

        playlist.getSongs().add(song);
        var updatedPlaylist = playlistRepository.save(playlist);
        log.info("Song successfully added to playlist");

        return mapToResponseDTO(updatedPlaylist);
    }

    @Transactional
    public PlaylistResponseDTO addSongsToPlaylist(Long playlistId, PlaylistAddSongsBatchRequestDTO requestDTO) {
        var playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new PlaylistNotFoundException("Playlist not found with id: " + playlistId));

        var currentSongs = playlist.getSongs();
        var songsToAdd = requestDTO.getSongIds().stream()
                .map(songId -> songRepository.findById(songId)
                        .orElseThrow(() -> new SongNotFoundException("Song not found with id: " + songId)))
                .collect(Collectors.toSet());

        var anySongAlreadyInPlaylist = songsToAdd.stream()
                .anyMatch(currentSongs::contains);

        if (anySongAlreadyInPlaylist) {
            throw new SongAlreadyInPlaylistException("One or more songs are already in the playlist");
        }

        playlist.getSongs().addAll(songsToAdd);
        var updatedPlaylist = playlistRepository.save(playlist);

        return mapToResponseDTO(updatedPlaylist);
    }



    public void deletePlaylist(Long id) {
        var playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new PlaylistNotFoundException("Playlist not found with id: " + id));
        playlistRepository.delete(playlist);
    }


    private PlaylistResponseDTO mapToResponseDTO(Playlist playlist) {
        var songs = playlist.getSongs();
        return PlaylistResponseDTO.builder()
                .id(playlist.getId())
                .title(playlist.getTitle())
                .userId(playlist.getUser().getId())
                .songs(songs)
                .build();
    }

    private Playlist mapDTOtoEntity(PlaylistRequestDTO requestDTO, User user) {
        var playlist = new Playlist();
        playlist.setTitle(requestDTO.getTitle());
        playlist.setUser(user);
        return playlist;
    }

}
