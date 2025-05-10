package com.tej.smart_lms.model;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    private String projectTitle;
    private String description;
    private List<String> technologies;
    private String difficulty;
    private List<Section> sections; // 🔥 Add this line


}