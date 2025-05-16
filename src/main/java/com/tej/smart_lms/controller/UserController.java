package com.tej.smart_lms.controller;

import com.tej.smart_lms.dto.UpdateProfileRequest;
import com.tej.smart_lms.model.User;
import com.tej.smart_lms.services.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(
            @RequestParam(name = "username", required = false) String usernameParam,
            HttpSession session) {

        // 1) Are they logged in?
        String sessionUser = (String) session.getAttribute("user");
        if (sessionUser == null) {
            return new ResponseEntity<>(
                    Map.of("error", "unauthorized"),
                    HttpStatus.UNAUTHORIZED
            );
        }

        // 2) If they supplied a username param, it must match their session user
        if (usernameParam != null && !usernameParam.equals(sessionUser)) {
            return new ResponseEntity<>(
                    Map.of("error", "unauthorized"),
                    HttpStatus.UNAUTHORIZED
            );
        }

        // 3) Determine whose profile to fetch
        String targetUser = (usernameParam != null && !usernameParam.isBlank())
                ? usernameParam
                : sessionUser;

        // 4) Fetch by the targetUser
        Optional<User> maybeUser = userService.getByUsername(targetUser);
        if (maybeUser.isEmpty()) {
            return new ResponseEntity<>(
                    Map.of("error", "User not found"),
                    HttpStatus.NOT_FOUND
            );
        }

        // 5) Return the user, stripping out the password
        User user = maybeUser.get();
        user.setPassword(null);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateProfile(
            @RequestBody UpdateProfileRequest updateProfileRequest, HttpSession session) {

        String username = (String) session.getAttribute("user");
        if (username == null) {
            return ResponseEntity.status(401).build(); // not logged in
        }
        Optional<User> updated = userService.updateProfile(username, updateProfileRequest.getUserName(), updateProfileRequest.getLocation(),
                updateProfileRequest.getPassword(), updateProfileRequest.getSkills());
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
