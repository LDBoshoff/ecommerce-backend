package com.ldb.backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ldb.backend.auth.JwtUtil;
import com.ldb.backend.model.Role;
import com.ldb.backend.model.User;
import com.ldb.backend.model.dto.LoginRequest;
import com.ldb.backend.model.dto.RegisterRequest;
import com.ldb.backend.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        try {
            if (!registerRequest.isValid()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email, password, first name, and last name are required.");
            }

            if (userService.emailExists(registerRequest.getEmail())) { 
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists"); // Change status code and message to not leak user info to bad actors
            }

            String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
            User user = new User(registerRequest.getEmail(), encodedPassword, registerRequest.getFirstName(), registerRequest.getLastName(), Role.USER);
            userService.createUser(user);

            return ResponseEntity.ok("Registration successful"); 
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed due to an error.");     
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            if (!loginRequest.isValid()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email and password are required.");
            }
            
            // Step 1: Create an authentication object
            Authentication authToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()); // email = principal, password = credential
    
            // Step 2: Authenticate the user
            authenticationManager.authenticate(authToken);

            // Step 3: Retrieve the user with login request details
            User user = userService.getUserByEmail(loginRequest.getEmail());
            
            if (user == null) {
                // Handle user not found (authentication failure)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found.");
            }

            // Step 4: Generate a JWT token and login response
            String token = jwtUtil.createToken(user);

            // Step 5: create response
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("userId", user.getId());

            // Step 6: Send the response
            return ResponseEntity.ok(response);
    
        } catch (AuthenticationException e) {
            // Handle authentication failure
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed.");
        } catch (Exception e) {
            // Handle any other potential exceptions (e.g., database errors)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login Error.");
        }
    }

}

