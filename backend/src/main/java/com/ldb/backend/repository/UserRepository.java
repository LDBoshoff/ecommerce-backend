package com.ldb.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ldb.backend.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}