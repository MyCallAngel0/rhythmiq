package com.endava.app.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="albums")
public class Album {
    @Id
    @Column(name="album_id")
    @GeneratedValue
    private Long id;
    @Column(name="title", length=30)
    private String title;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id")
    private com.endava.app.domain.User user;
}
