package com.simonlai.doit.controller;

import com.simonlai.doit.dto.LoginRequest;
import com.simonlai.doit.dto.RegisterRequest;
import com.simonlai.doit.model.User;
import com.simonlai.doit.repository.UserRepository;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Check user existent by username
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElse(null);

        // Authenticate password
        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            // Successfully authenticated
            return ResponseEntity.ok().body(Map.of(
                    "message", "User logged in successfully!",
                    "username", user.getUsername()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "message", "Invalid username or password"));
        }
    }
}
