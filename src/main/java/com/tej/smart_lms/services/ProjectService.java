package com.tej.smart_lms.services;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.tej.smart_lms.dto.Project;
import com.tej.smart_lms.model.User;
import com.tej.smart_lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private UserRepository userRepository;

   @Autowired private DataLoaderService dataLoaderService;

    public List<Project> getAllProjects() {
        return dataLoaderService.getProjects();
    }

    public List<Project> getByDifficulty(String id) {
        return dataLoaderService.getProjects().stream()
                .filter(p -> p.getId() != null && p.getId().equalsIgnoreCase(id))
                .collect(Collectors.toList());
    }

    public void markProjectCompleted(String username, String projectTitle) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Set<String> completed = user.getCompletedProjects();
        completed.add(projectTitle);
        user.setCompletedProjects(completed);
        userRepository.save(user);
    }

    public int getCompletedProjectsCount(String username) {
        User user = userRepository.findByUsername(username).orElseThrow();
        return user.getCompletedProjects() != null ? user.getCompletedProjects().size() : 0;
    }

    public List<String> getCompletedProjects(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            Set<String> completed = user.getCompletedProjects();
            if (completed != null) {
                return new ArrayList<>(completed);
            }
        }
        return Collections.emptyList();
    }
}