package com.ldb.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.ldb.backend.model.Product;
import com.ldb.backend.repository.ProductRepository;
import java.util.List;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    // Get a single product - @GetMapping("/{productId}")
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }


    public Product updateProduct(Long productId, Product updatedProduct) {
        // Check if the product with the given ID exists in the database
        Product existingProduct = productRepository.findById(productId).orElse(null);

        if (existingProduct != null) {
            // Product with the specified ID exists, so update it directly
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setQuantity(updatedProduct.getQuantity());

            // Save the updated product to the database
            return productRepository.save(existingProduct);
        }

        // Product with the given ID doesn't exist, return null
        return null;
    }

    public boolean deleteProduct(Long id) {
        try {
            productRepository.deleteById(id);
            return true; // Deletion successful
        } catch (EmptyResultDataAccessException ex) {
            return false; // Deletion failed
        }

    }
}
