package com.ldb.backend.repository;

import com.ldb.backend.model.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Save a new product or update an existing one
    // Product save(Product entity);

    // Delete a product by its ID
    // void deleteById(ID id);

    // Check if a product with a specific ID exists
    // boolean existsById(ID id);

    // Find a product by its ID
    // Optional<Product> findById(ID id);

    // Find all products
    // List<Product> findAll();

    // Find all products by store ID
    List<Product> findByStoreId(Long storeId);

    // Find the number of products
    // long count();

    // Delete a product
    // void delete(Product entity);

    // Delete multiple products
    // void deleteAll(Iterable<? extends Product> entities);

    // Find products by example
    // <S extends Product> List<Product> findAll(Example<S> example);

    // Find products by example with sorting and matching
    // <S extends Product> List<Product> findAll(Example<S> example, Sort sort);

    // Flush changes made during the transaction
    // void flush();
    
    // Find all products and sort them
    // List<Product> findAll(Sort sort);

    // Save multiple products
    // <S extends Product> List<Product> saveAll(Iterable<S> entities);
}
