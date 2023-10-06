package com.ldb.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ldb.backend.model.Product;
import com.ldb.backend.repository.ProductRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product updateProduct(Long productId, Product updatedProduct) {
        // Check if the product with the given ID exists in the database
        Product existingProduct = productRepository.findById(productId).orElse(null);

        if (existingProduct != null) {
            // Product with the specified ID exists, so update it directly
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setStockQuantity(updatedProduct.getStockQuantity());

            // Save the updated product to the database
            return productRepository.save(existingProduct);
        }

        // Product with the given ID doesn't exist, return null
        return null;
    }

    public boolean deleteProduct(Long id) {
        // Attempt to delete the product by its ID
        if (productRepository.existsById(id)) {
            try {
                productRepository.deleteById(id);
                return true; // Deletion was successful
            } catch (Exception e) {
                // Handle any exception that might occur during deletion (e.g., database errors)
                return false; // Deletion failed
            }
        }
        return false; // Product with the given ID doesn't exist

    }
}
