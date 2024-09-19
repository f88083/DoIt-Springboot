package com.simonlai.doit.controller;

import com.simonlai.doit.dto.LoginRequest;
import com.simonlai.doit.dto.RegisterRequest;
import com.simonlai.doit.model.User;
import com.simonlai.doit.repository.UserRepository;
import com.simonlai.doit.security.jwt.JwtUtil;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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

    @Autowired
    private JwtUtil jwtUtil;

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
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        // Check user existent by username
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElse(null);

        // Authenticate password
        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            // Prepare token
            String jwtToken = jwtUtil.generateToken(user.getUsername());

            // Prepare cookie
            Cookie cookie = new Cookie("jwt", jwtToken);
            // cookie.setHttpOnly(true);
            // cookie.setSecure(true);
            // cookie.setSameSite(SameSite.LAX);
            // FIXME: cookie time zone seems to be different from the local =
            cookie.setMaxAge(24 * 60 * 60);// expires in 1 day
            cookie.setPath("/");
            response.addCookie(cookie);

            // Successfully authenticated
            return ResponseEntity.ok().body(Map.of(
                    "message", "User logged in successfully!",
                    "username", user.getUsername()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid username or password"));
        }
    }
}
