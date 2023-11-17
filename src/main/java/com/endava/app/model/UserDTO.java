package com.endava.app.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDate;

@Getter
@Setter
public class UserDTO {

    private Long id;

    @NotBlank(message = "First name must not be blank")
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    private String lastName;

    @UniqueElements(message = "This account name is already used")
    @NotBlank(message = "Account name must nor be blank")
    private String accountName;

    @Email(message = "provide a valid email address")
    @UniqueElements(message = "This email is already being used")
    private String email;

    @NotBlank(message = "Date of birth must not be blank")
    private LocalDate dob;

}
