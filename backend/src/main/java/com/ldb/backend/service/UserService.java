package com.ldb.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.ldb.backend.model.User;
import com.ldb.backend.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    // @Autowired
    // private PasswordEncoder passwordEncoder;

    // retrieves user via id or else return null if not found
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    // saves a user to the database
    public User createUser(User saveUser) {
        return userRepository.save(saveUser);
    }
    
    // checks if an email already exists or not
    public boolean uniqueEmail(String email) { 
        if (userRepository.existsByEmail(email)){
            return false;
        }
        
        return true;
    }

    // retrieves a user by their email address
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    // attempts to register a new user
    public boolean registeredUser(User registrationUser) {
        
        if (!uniqueEmail(registrationUser.getEmail())) {
            return false;
        }

        User createUser = new User();

        createUser.setEmail(registrationUser.getEmail());
        createUser.setPassword(registrationUser.getPassword());
        createUser.setFirstName(registrationUser.getFirstName());
        createUser.setLastName(registrationUser.getLastName());

        User newUser = createUser(createUser);
        
        if (userRepository.existsById(newUser.getId())) {
            return true;
        }
        
        return false;
    }

    // attempts to delete a user
    public boolean deleteUser(Long userId) {
        try {
            userRepository.deleteById(userId);
            return true; // Deletion successful
        } catch (EmptyResultDataAccessException ex) {
            return false; // Deletion failed
        }
        
    }

    // updates an existing user
    public User updateUser(Long userId, User updatedUser) {
        // Find the user by ID
        User existingUser = userRepository.findById(userId).orElse(null);

        if (existingUser != null) {
            
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());

            // Save the updated user
            return userRepository.save(existingUser);
        }

        return null;
    }
}