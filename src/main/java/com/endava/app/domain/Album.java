package com.endava.app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "albums")
public class Album {

    @Id
    @Column(name = "album_id")
    @GeneratedValue
    private Long id;

    @Column(name = "title", length = 30)
    private String title;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "artist_id")
    private User user;
}
