package com.endava.app.model.response;

import com.endava.app.domain.Album;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserResponse {
    private Long id;

    private String firstname;

    private String lastname;

    private String username;

    private String email;

    private LocalDate dob;

    private List<Album> albums;
}
