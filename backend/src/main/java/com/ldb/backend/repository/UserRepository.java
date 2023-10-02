package com.ldb.backend.repository;

import com.ldb.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // Custom query method to find a user by their email
    User findByEmail(String email);
}
