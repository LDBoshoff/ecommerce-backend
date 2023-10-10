package com.ldb.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ldb.backend.model.User;
import com.ldb.backend.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    // ensure unique email
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User registrationUser) {

        if (registrationUser == null || registrationUser.getEmail() == null || registrationUser.getPassword() == null) {
            return ResponseEntity.badRequest().body("Invalid or incomplete user data");
        }

        boolean registered = userService.registeredUser(registrationUser);

        if (registered) {
            return ResponseEntity.ok("Registration successful");
        }
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Registration failed");
    }


}
