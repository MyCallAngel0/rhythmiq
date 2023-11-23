package com.endava.app.Services;

import com.endava.app.domain.Song;
import com.endava.app.repos.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {

    private final SongRepository songRepository;

    @Autowired
    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    public Song getSongById(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found with id: " + id));
    }

    public Song addSong(Song song) {
        return songRepository.save(song);
    }

    public Song updateSong(Long id, Song songDetails) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Song not found with id: " + id));
        song.setTitle(songDetails.getTitle());
        // Обновите другие поля, если они есть
        return songRepository.save(song);
    }

    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }
}
