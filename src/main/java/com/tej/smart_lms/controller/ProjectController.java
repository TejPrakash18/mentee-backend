package com.tej.smart_lms.controller;


import com.tej.smart_lms.model.Project;
import com.tej.smart_lms.model.User;
import com.tej.smart_lms.services.ProjectService;
import com.tej.smart_lms.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/projects/filter")
    public ResponseEntity<List<Project>> getByDifficulty(@RequestParam String level) {
        return ResponseEntity.ok(projectService.getByDifficulty(level));
    }

    @PostMapping("/user/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.register(user));
    }

    @PostMapping("/user/complete")
    public ResponseEntity<Void> completeProject(@RequestParam String username, @RequestParam String title) {
        userService.markProjectCompleted(username, title);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/profile")
    public ResponseEntity<User> getProfile(@RequestParam String username) {
        Optional<User> user = userService.getByUsername(username);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/user/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        boolean success = userService.login(username, password);
        if (success) return ResponseEntity.ok("Login successful");
        else return ResponseEntity.status(401).body("Invalid credentials");
    }

    @PutMapping("/user/update")
    public ResponseEntity<User> updateProfile(
            @RequestParam String username,
            @RequestParam(required = false) String location,
            @RequestBody(required = false) Set<String> skills) {
        Optional<User> updated = userService.updateProfile(username, location, skills);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}