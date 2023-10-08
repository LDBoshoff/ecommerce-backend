package com.ldb.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.ldb.backend.service.ProductService;
import com.ldb.backend.model.Product;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // GET a list of all products
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    // GET a single product by ID
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);

        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            // Return a 404 Not Found response if the product is not found
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product, UriComponentsBuilder ucb) {
        Product createdProduct = productService.createProduct(product);

        URI locationOfNewProduct = ucb
                .path("api/products/{productId}")
                .buildAndExpand(createdProduct.getId())
                .toUri();
        
        return ResponseEntity.created(locationOfNewProduct).body(createdProduct);
    }

    // UPDATE an existing product
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product updatedProduct) {
        Product updated = productService.updateProduct(productId, updatedProduct);

        if (updated != null) {
            // Product was updated successfully 
            return ResponseEntity.noContent().build();
        } else {
            // Product with the specified ID doesn't exist
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE a product by ID
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        if (productService.deleteProduct(productId)) { 
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}