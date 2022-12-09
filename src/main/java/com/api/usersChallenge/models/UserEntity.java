package com.api.usersChallenge.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotEmpty(message = "You should provide a Name")
    @Column(name = "name", nullable = false)
    private String name;

    @NotEmpty(message = "Email cannot be empty")
    @Column(name = "password", nullable = false)
    private String password;
    @Email(message = "Email is not valid")
    @NotEmpty(message = "Email cannot be empty")
    @Column(name = "email", nullable = false)
    private String email;
    @OneToMany(
            mappedBy = "userId",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PhoneEntity> phone = new ArrayList<>();

}