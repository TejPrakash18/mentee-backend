// --- BlogSection.java ---
package com.tej.smart_lms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
public class BlogSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore // 👈 hide section id too
    private Long id;

    private String title;
    private String type;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String language;

    @Column(columnDefinition = "TEXT")
    private String content;
}
