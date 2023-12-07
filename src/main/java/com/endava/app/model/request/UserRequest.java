package com.endava.app.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class UserRequest {

    private Long id;

    @NotBlank(message = "First name must not be blank")
    private String firstname;

    @NotBlank(message = "Last name must not be blank")
    private String lastname;

    @NotBlank(message = "Account name must nor be blank")
    @UniqueElements(message = "This account name is already used")
    @Size(min = 3, max = 50)
    private String username;

    @Email(message = "provide a valid email address")
    @UniqueElements(message = "This email is already being used")
    private String email;

    @NotBlank(message = "Date of birth must not be blank")
    private LocalDate dob;

    private Set<String> albums;
}
