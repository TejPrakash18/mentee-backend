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

    public Optional<User> updateProfile(String username, String newName, String email,
                                        String location, String college, String newPassword, Set<String> skills) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            if (location != null) user.setLocation(location);
            if (skills != null) user.setSkills(skills); // Avoid duplicate merge
            if (newName != null) user.setName(newName);
            if (college != null) user.setCollege(college);
            if (email != null) user.setEmail(email);
            if (newPassword != null) user.setPassword(passwordEncoder.encode(newPassword));

            return Optional.of(userRepository.save(user));
        }
        return Optional.empty();
    }

}