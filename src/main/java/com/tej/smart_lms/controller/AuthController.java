package com.tej.smart_lms.controller;


import com.tej.smart_lms.dto.ForgotPasswordRequest;
import com.tej.smart_lms.dto.LoginRequest;
import com.tej.smart_lms.model.User;
import com.tej.smart_lms.services.AuthService;
import com.tej.smart_lms.services.UserService;
import com.tej.smart_lms.utils.JwtUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")

public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired private AuthService authService;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/register")


    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.ok(authService.register(user));
    }

    @PostMapping("/login")


    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        boolean success = authService.login(request.getUsername(), request.getPassword());
        if (success) {
            String token = jwtUtil.generateToken(request.getUsername());
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
    @PostMapping("/forgot-password")

    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        String message = authService.updateForgottenPassword(request);
        if (message.equals("Password updated successfully.")) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.badRequest().body(message);
        }
    }



    @PostMapping("/logout")

    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out");
    }



}
