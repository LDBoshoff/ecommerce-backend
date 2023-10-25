package com.ldb.backend.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
        if (!storeBelongsToPrincipal(store, principal)) {
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

        
        if ((!storeBelongsToPrincipal(product.getStore(), principal)) || (storeId != (product.getStore().getId()))) {    // Prevents the principal from accessing their product which belongs their different store than the current one in pathvariable
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<String> addProduct(@PathVariable Long storeId, @RequestBody Product product, Principal principal) {
        Store store = storeService.getStoreById(storeId);

        // check if product attributes are null or empty
        if (product.getName() == null || product.getName().isEmpty() ||
        product.getDescription() == null || product.getDescription().isEmpty() ||
        product.getPrice() == null || product.getQuantity() == null) {
            return ResponseEntity.badRequest().body("Store name cannot be empty.");
        }

        // check if product with desired name already exists
        if (productService.existsByName(product)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A product with the same name already exists.");
        }

        // check if the store in which a new product is to be created belongs to the principal
        if (!storeBelongsToPrincipal(store, principal)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        product.setStore(store);
        productService.createProduct(product);

        return ResponseEntity.ok("Product created successfully."); // change http status to CREATED
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long storeId, @PathVariable Long productId, Principal principal) {
        Product product = productService.getProductById(productId); // get the product

        if (product == null) {                        // check if product is null or not
            return ResponseEntity.notFound().build(); // refactor status code to prevent data leak
        }
        
        if ((!storeBelongsToPrincipal(product.getStore(), principal)) || (storeId != (product.getStore().getId()))) {               // ensure principal is authorized to access this product of specific store
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        productService.deleteProductById(productId); // If the user owns the store, delete the product

        return ResponseEntity.ok("Product deleted successfully."); 
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long storeId, @PathVariable Long productId, @RequestBody Product updatedProduct, Principal principal) {
        Product product = productService.getProductById(productId); // get the product

        if (product == null) {                        // check if product is null or not
            return ResponseEntity.notFound().build(); // refactor status code to prevent data leak
        }
        
        if ((!storeBelongsToPrincipal(product.getStore(), principal)) || (storeId != (product.getStore().getId()))) {     // ensure principal is authorized to access this product of specific store
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        // Update the resource's attributes with the new values
        product.setName(updatedProduct.getName()); // error: name allowed to be empty value
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice()); // error: prices can be negative
        product.setQuantity(updatedProduct.getQuantity());

        product = productService.createProduct(product);
    
        return ResponseEntity.ok(product);  
    }

    // authorization method which verifies the store belongs to principal
    private boolean storeBelongsToPrincipal(Store store, Principal principal) {
        String email = principal.getName();
        return store.getUser().getEmail().equals(email);
    }

    // checks if the product id and store id path variables match
    private boolean isProductStoreMatching(Product product, Long storeId) {
        return storeId.equals(product.getStore().getId());
    }

}