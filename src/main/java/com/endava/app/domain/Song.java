package com.endava.app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "songs")
public class Song {

    @Id
    @Column(name = "song_id")
    @GeneratedValue
    private Long id;

    @Column(name = "song_title")
    private String title;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "artist_id")
    private User artist;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "album_id")
    private Album album;

    @ManyToMany(mappedBy = "songs")
    private Set<Playlist> playlists;
}
