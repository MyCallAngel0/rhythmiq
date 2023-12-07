package com.endava.app;

import com.endava.app.domain.Playlist;
import com.endava.app.domain.User;
import com.endava.app.model.response.PlaylistResponse;
import com.endava.app.model.request.PlaylistRequest;
import com.endava.app.repos.PlaylistRepository;
import com.endava.app.repos.UserRepository;
import com.endava.app.services.PlaylistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;


@ExtendWith(MockitoExtension.class)
public class PlaylistServiceTest {

    @Mock
    private PlaylistRepository playlistRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PlaylistService playlistService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void findAll_ReturnsPlaylistRequestList() {

        User user = new User();
        user.setUsername("exampleUser");

        Playlist playlist1 = new Playlist();
        playlist1.setId(1L);
        playlist1.setTitle("Playlist1");
        playlist1.setUser(user);
        playlist1.setSongs(new TreeSet<>());

        Playlist playlist2 = new Playlist();
        playlist2.setId(2L);
        playlist2.setTitle("Playlist2");
        playlist2.setUser(user);
        playlist2.setSongs(new TreeSet<>());

        List<Playlist> playlists = Arrays.asList(playlist1, playlist2);

        when(playlistRepository.findAll(Sort.by("id"))).thenReturn(playlists);

        List<PlaylistResponse> result = playlistService.findAll();

        assertEquals(2, result.size());
        assertEquals("Playlist1", result.get(0).getTitle());
        assertEquals("Playlist2", result.get(1).getTitle());
    }

    @Test
    void get_ReturnsPlaylistResponseForGivenId() {

        Long playlistId = 1L;
        User user = new User();
        user.setUsername("exampleUser");

        Playlist playlist = new Playlist();
        playlist.setId(playlistId);
        playlist.setTitle("Playlist");
        playlist.setUser(user);
        playlist.setSongs(new TreeSet<>());

        when(playlistRepository.findById(playlistId)).thenReturn(Optional.of(playlist));

        PlaylistResponse result = playlistService.get(playlistId);

        assertEquals("Playlist", result.getTitle());
        assertEquals(playlistId, result.getId());
        assertEquals("exampleUser", result.getUser());
    }

    @Test
    void create_ReturnsPlaylistId() {

        User user = new User();
        user.setUsername("exampleUser");

        PlaylistRequest playlistReq = new PlaylistRequest();
        playlistReq.setTitle("Playlist");
        playlistReq.setUser("exampleUser");

        Playlist playlist = new Playlist();
        playlist.setId(1L);
        playlist.setTitle("Playlist");
        playlist.setUser(user);
        playlist.setSongs(new TreeSet<>());

        when(userRepository.findByName("exampleUser")).thenReturn(user);
        when(playlistRepository.save(any(Playlist.class))).thenReturn(playlist);
        Long result = playlistService.create(playlistReq);
        assertEquals(1L, result);
    }

    @Test
    void update_UpdatesExistingPlaylist() {

        Long playlistId = 1L;
        PlaylistRequest playlistReq = new PlaylistRequest();
        playlistReq.setTitle("Updated Playlist");

        Playlist existingPlaylist = new Playlist();
        existingPlaylist.setId(playlistId);
        existingPlaylist.setTitle("Original Playlist");
        existingPlaylist.setUser(new User());
        existingPlaylist.setSongs(new TreeSet<>());

        when(playlistRepository.findById(playlistId)).thenReturn(Optional.of(existingPlaylist));
        when(playlistRepository.save(any(Playlist.class))).thenReturn(existingPlaylist);
        playlistService.update(playlistId, playlistReq);
        assertEquals("Updated Playlist", existingPlaylist.getTitle());
    }

    @Test
    void delete_RemovesPlaylist() {
        Long playlistId = 1L;
        playlistService.delete(playlistId);
        verify(playlistRepository).deleteById(playlistId);
    }

}