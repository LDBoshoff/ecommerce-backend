package com.ldb.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ldb.backend.model.User;
import com.ldb.backend.repository.UserRepository;

@Service // This annotation marks this class as a Spring service component.
public class UserService {
    @Autowired // This annotation injects the UserRepository bean into this service.
    private UserRepository userRepository;

    @Autowired // This annotation injects the PasswordEncoder bean into this service.
    private PasswordEncoder passwordEncoder;

    // This method registers a new user by saving their information to the database.
    public void registerUser(User user) {
        // Encode the user's password before saving it to ensure security.
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user object, including the encoded password, to the UserRepository.
        userRepository.save(user);
    }

    // This method retrieves a user by their email address from the UserRepository.
    public User getUserByEmail(String email) {
        // Use the findByEmail method provided by the UserRepository to find a user by email.
        return userRepository.findByEmail(email);
    }

    
}
