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

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> updateProfile(String username, String newName, String location, String newPassword,Set<String> skills) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (location != null) user.setLocation(location);
            if (skills != null) user.getSkills().addAll(skills);
            if (newName != null) user.setName(newName);
            if (newPassword != null) user.setPassword(passwordEncoder.encode(user.getPassword()));
            return Optional.of(userRepository.save(user));
        }
        return Optional.empty();
    }
}