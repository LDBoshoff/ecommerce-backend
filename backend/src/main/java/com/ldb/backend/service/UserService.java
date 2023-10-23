package com.ldb.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ldb.backend.model.User;
import com.ldb.backend.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            throw new UsernameNotFoundException("User with email " + email + " not found.");
        }
        
        UserDetails userDetails = user;
        
        return userDetails;
    }

    // Password is hashed before calling createUser
    public User createUser(User saveUser) {
        // saveUser.setPassword(passwordEncoder.encode(saveUser.getPassword())); // Hash the password before saving
        return userRepository.save(saveUser);
    }

    // retrieves user via id or else return null if not found
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public String getEmailById(Long userId) {
        return userRepository.findEmailById(userId).orElse(null);
    }

    // If a existsByEmail equals true the email is not unique
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    // retrieves a user by their email address
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
    
}
