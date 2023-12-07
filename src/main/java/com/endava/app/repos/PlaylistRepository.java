package com.endava.app.repos;

import com.endava.app.domain.Playlist;
import com.endava.app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    @Query("SELECT p FROM Playlist p JOIN p.user u WHERE u.username = :username")
    List<Playlist> findAllByUser(String username);
}
