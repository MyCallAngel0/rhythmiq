package com.endava.app.Services;

import com.endava.app.domain.Playlist;
import com.endava.app.repos.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    @Autowired
    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    public Playlist getPlaylistById(Long id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playlist not found with id: " + id));
    }

    public Playlist addPlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public Playlist updatePlaylist(Long id, Playlist playlistDetails) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Playlist not found with id: " + id));
        // Обновите поля playlist согласно playlistDetails
        return playlistRepository.save(playlist);
    }

    public void deletePlaylist(Long id) {
        playlistRepository.deleteById(id);
    }
}
