package com.tej.smart_lms.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Version
    private Long version; // for optimistic locking

    private String title;
    private String difficulty; // Optional: easy, medium, etc.
    private String category;   // Technical, Projects, Core Fundamental
    private List<String> tags; // Flexible tags like ["spring", "java"]


    @Column(columnDefinition = "TEXT")
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> technologies; // Optional for project-related blogs

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "blog_id")
    private List<BlogSection> sections;
}
