package com.tej.smart_lms.services;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.tej.smart_lms.model.Project;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private List<Project> projects;

    @PostConstruct
    public void init() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        projects = Arrays.asList(mapper.readValue(
                new ClassPathResource("projects.json").getFile(),
                Project[].class));
    }

    public List<Project> getAllProjects() {
        return projects;
    }

    public List<Project> getByDifficulty(String difficulty) {
        return projects.stream()
                .filter(p -> p.getDifficulty() != null && p.getDifficulty().equalsIgnoreCase(difficulty))
                .collect(Collectors.toList());
    }

}