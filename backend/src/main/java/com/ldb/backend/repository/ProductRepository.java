package com.ldb.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ldb.backend.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Available methods provided by JpaRepository:

    // Save a product entity to the database
    // Product save(Product product);

    // Retrieve a product by its primary key (ID)
    // Returns an Optional<Product> to handle cases where the product may not exist
    // Optional<Product> findById(Long id);

    // Retrieve all products in the database
    // List<Product> findAll();

    // Save a collection of product entities to the database
    // List<Product> saveAll(Iterable<Product> products);

    // Delete a product entity from the database
    // void delete(Product product);

    // Delete all product entities from the database
    // void deleteAll();

    // Delete product entities by their primary keys (IDs)
    // void deleteAllByIdIn(Iterable<Long> ids);

    // Check if a product entity exists in the database by its primary key (ID)
    // boolean existsById(Long id);

    // Count the number of product entities in the database
    // long count();
}
