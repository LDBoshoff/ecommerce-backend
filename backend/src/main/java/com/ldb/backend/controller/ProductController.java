package com.ldb.backend.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ldb.backend.model.Product;
import com.ldb.backend.model.Store;
import com.ldb.backend.service.ProductService;
import com.ldb.backend.service.StoreService;

@RestController
@RequestMapping("/api/stores/{storeId}/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private StoreService storeService;

    @GetMapping
    public ResponseEntity<?> getProducts(@PathVariable Long storeId, Principal principal) {
        Store store = storeService.getStoreById(storeId);
        // check if store == null, return error if so
        if (!authorized(store, principal)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("store that product belongs to not principals store");
        }

        List<Product> products = productService.getProductsByStoreId(storeId);

        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(products);
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long storeId, @PathVariable Long productId, Principal principal) {
        Product product = productService.getProductById(productId);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        
        if ((!authorized(product.getStore(), principal)) || // Checks if the authenticated user owns the store that the product belongs to
            (storeId != (product.getStore().getId()))) {    // Prevents the principal from calling their product which belongs their different store
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(product);
    }

    // fix error: allows user to create exact duplicate products
    @PostMapping
    public ResponseEntity<String> addProduct(@PathVariable Long storeId, @RequestBody Product product, Principal principal) {
        Store store = storeService.getStoreById(storeId);

        // check if product attributes are null or empty
        if (product.getName() == null || product.getName().isEmpty() ||
        product.getDescription() == null || product.getDescription().isEmpty() ||
        product.getPrice() == null || product.getQuantity() == null) {
            return ResponseEntity.badRequest().body("Store name cannot be empty.");
        }

        // check if products already exists

        if (!authorized(store, principal)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("store that product belongs to not principals store");
        }

        product.setStore(store);
        productService.createProduct(product);

        return ResponseEntity.ok("Product created successfully."); // change http status to CREATED
    }

    // authorization method which verifies product's linked store belongs to principal
    private boolean authorized(Store store, Principal principal) {
        String email = principal.getName();

        return store.getUser().getEmail().equals(email);
    }


}