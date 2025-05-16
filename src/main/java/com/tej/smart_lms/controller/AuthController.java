package com.tej.smart_lms.controller;


import com.tej.smart_lms.dto.LoginRequest;
import com.tej.smart_lms.model.User;
import com.tej.smart_lms.services.AuthService;
import com.tej.smart_lms.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(authService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request, HttpSession session) {
        boolean success = authService.login(request.getUsername(), request.getPassword());
        if (success) {
            session.setAttribute("user", request.getUsername());
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out");
    }

}
