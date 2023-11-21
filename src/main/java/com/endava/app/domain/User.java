package com.endava.app.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDate;

@Entity
@Data
public class User {
    @Id
    @Column(name="user_id")
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "primary_sequence"
    )
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="account_name", unique = true)
    private String accountName;

    @Column(unique = true)
    private String email;

    @Column(name="date_of_birth")
    private LocalDate dob;

}
