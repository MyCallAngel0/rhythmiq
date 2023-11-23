package com.endava.app.repos;

import com.endava.app.domain.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface SongRepository extends JpaRepository<Song, Long> {
}

