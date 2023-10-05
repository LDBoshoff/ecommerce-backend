package com.ldb.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ldb.backend.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
