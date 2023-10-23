package com.ldb.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ldb.backend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    public boolean existsByEmail(String email);
    public Optional<User> findByEmail(String email);
    
    @Query("SELECT u.email FROM User u WHERE u.id = :userId")
    Optional<String> findEmailById(@Param("userId") Long userId);
    
}