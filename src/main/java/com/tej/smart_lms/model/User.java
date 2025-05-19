package com.tej.smart_lms.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Column(unique = true)
    @NotBlank(message = "Username is required")
    private String username;

    @Column(unique = true)
    @NotBlank(message = "Username is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private String college;
    private String location;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> skills = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> completedProjects = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> completedDsaQuestion = new HashSet<>();

}