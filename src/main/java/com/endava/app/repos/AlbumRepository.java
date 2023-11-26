package com.endava.app.repos;

import com.endava.app.domain.Album;
import com.endava.app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    @Query("SELECT a FROM Album a WHERE a.title = :title")
    Album findByTitle(@Param("title") String title);

    Set<Album> getAlbumsByUser(User user);

    @Query("SELECT a.title FROM Album a WHERE a.user = :user")
    Set<String> getAlbumTitlesByUser(@Param("user") User user);

}
