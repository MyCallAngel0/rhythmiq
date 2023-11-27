package com.endava.app.services;

import com.endava.app.domain.User;
import com.endava.app.model.UserDTO;
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

    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("id"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }

    public UserDTO get(final Long id) {
        return userRepository.findById(id)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(UserNotFoundException::new);
    }

    public Long create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getId();
    }

    @Transactional
    public void update(final Long id, final UserDTO userDTO) {
        final User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        mapToEntity(userDTO, user);
        log.info("User with id {} was successfully updated", id);
        userRepository.save(user);
    }

    @Transactional
    public void delete(final Long id) {
        userRepository.deleteById(id);
        log.info("User with id {} was successfully deleted", id);
    }

    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setAccountName(user.getAccountName());
        userDTO.setEmail(user.getEmail());
        userDTO.setDob(user.getDob());
        userDTO.setAlbums(albumRepository.getAlbumTitlesByUser(user));
        return userDTO;
    }

    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setAccountName(userDTO.getAccountName());
        user.setEmail(userDTO.getEmail());
        user.setDob(userDTO.getDob());
        return user;
    }
}
