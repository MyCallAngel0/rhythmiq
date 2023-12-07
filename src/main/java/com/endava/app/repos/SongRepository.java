package com.endava.app.repos;

import com.endava.app.domain.Album;
import com.endava.app.domain.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {
    @Query("SELECT s.title FROM Song s JOIN Playlist p WHERE p.id = :playlistId")
    List<String> findByPlaylistId(@Param("playlistId") Long playlistId);

    @Query("SELECT s FROM Song s WHERE s.album = :album")
    List<Song> findAllByAlbum(@Param("album") Album album);
}
