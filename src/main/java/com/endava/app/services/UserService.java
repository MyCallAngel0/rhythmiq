package com.endava.app.services;

import com.endava.app.domain.User;
import com.endava.app.model.request.UserRequest;
import com.endava.app.model.response.UserResponse;
import com.endava.app.repos.AlbumRepository;
import com.endava.app.repos.UserRepository;
import com.endava.app.util.exceptions.user.UserNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;

    public List<UserResponse> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToResponse(user, new UserResponse()))
                .toList();
    }

    public UserResponse getByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> mapToResponse(user, new UserResponse()))
                .orElseThrow(UserNotFoundException::new);
    }

    public UserResponse get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToResponse(user, new UserResponse()))
                .orElseThrow(UserNotFoundException::new);
    }

    public Long create(final UserRequest userRequest) {
        final User user = new User();
        mapToEntity(userRequest, user);
        return userRepository.save(user).getId();
    }

    @Transactional
    public void update(final Long id, final UserRequest userRequest) {
        final User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        mapToEntity(userRequest, user);
        log.info("User with id {} was successfully updated", id);
        userRepository.save(user);
    }

    @Transactional
    public void delete(final Long id) {
        userRepository.deleteById(id);
        log.info("User with id {} was successfully deleted", id);
    }

    private UserResponse mapToResponse(final User user, final UserResponse userResponse) {
        userResponse.setId(user.getId());
        userResponse.setFirstname(user.getFirstname());
        userResponse.setLastname(user.getLastname());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setDob(user.getDob());
        userResponse.setAlbums(albumRepository.getAlbumByUser(user));
        return userResponse;
    }

    private User mapToEntity(final UserRequest userRequest, final User user) {
        user.setFirstname(userRequest.getFirstname());
        user.setLastname(userRequest.getLastname());
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setDob(userRequest.getDob());
        return user;
    }
}
