package com.tej.smart_lms.controller;


import com.tej.smart_lms.dto.Project;
import com.tej.smart_lms.services.ProjectService;
import com.tej.smart_lms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")

public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Project>> getById(@RequestParam String id) {
        return ResponseEntity.ok(projectService.getByDifficulty(id));
    }

    @PostMapping("/complete")
    public ResponseEntity<Void> completeProject(@RequestParam String username, @RequestParam String title) {
        projectService.markProjectCompleted(username, title);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/completed/count")
    public ResponseEntity<Integer> getCompletedProjectsCount(@RequestParam String username) {
        int count = projectService.getCompletedProjectsCount(username);
        return ResponseEntity.ok(count);
    }
}