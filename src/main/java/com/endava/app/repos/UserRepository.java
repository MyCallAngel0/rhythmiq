package com.endava.app.repos;

import com.endava.app.domain.Album;
import com.endava.app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = :name")
    User findByName(@Param("name") String name);

    @Query("SELECT a FROM Album a WHERE a.user = :user")
    List<Album> findAllUserAlbums(@Param("user") User user);

    Optional<User> findByUsername(String username);



    Optional<User> findByEmail(String email);

}
