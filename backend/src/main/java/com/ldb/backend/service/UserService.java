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

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public boolean deleteUser(Long userId) {
        try {
            userRepository.deleteById(userId);
            return true; // Deletion successful
        } catch (EmptyResultDataAccessException ex) {
            return false; // Deletion failed
        }
        
    }

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

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

}
