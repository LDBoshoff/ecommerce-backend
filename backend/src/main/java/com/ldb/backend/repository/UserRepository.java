package com.ldb.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ldb.backend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    public boolean existsByEmail(String email);
    public Optional<User> findByEmail(String email);

}