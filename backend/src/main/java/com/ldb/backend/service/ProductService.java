package com.ldb.backend.service;

import com.ldb.backend.model.Product;
import com.ldb.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProductsByStoreId(Long storeId) {
        return productRepository.findByStoreId(storeId);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public boolean productExists(Product product) {
        return productRepository.existsByName(product.getName());
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        if (productRepository.existsById(id)) {
            updatedProduct.setId(id);
            return productRepository.save(updatedProduct);
        } else {
            return null; 
        }
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}
