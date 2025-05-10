package com.tej.smart_lms.services;




import com.tej.smart_lms.model.User;
import com.tej.smart_lms.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void markProjectCompleted(String username, String projectTitle) {
        User user = userRepository.findByUsername(username).orElseThrow();
        Set<String> completed = user.getCompletedProjects();
        completed.add(projectTitle);
        user.setCompletedProjects(completed);
        userRepository.save(user);
    }

    public boolean login(String username, String rawPassword) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        return userOpt.isPresent() && passwordEncoder.matches(rawPassword, userOpt.get().getPassword());
    }

    public Optional<User> updateProfile(String username, String location, Set<String> skills) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (location != null) user.setLocation(location);
            if (skills != null) user.getSkills().addAll(skills);
            return Optional.of(userRepository.save(user));
        }
        return Optional.empty();
    }
}