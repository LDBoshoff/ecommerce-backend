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

import com.ldb.backend.model.Store;
import com.ldb.backend.model.User;
import com.ldb.backend.service.StoreService;
import com.ldb.backend.service.UserService;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    @Autowired
    StoreService storeService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Store>> getUserStores(Principal principal) {
        String email = principal.getName();
        User user = userService.getUserByEmail(email);

        List<Store> stores = storeService.getStoresByUserId(user.getId());

        if (stores.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(stores);
        }

    }

    @GetMapping("/{storeId}")
    public ResponseEntity<Store> getStoreById(@PathVariable Long storeId, Principal principal) {
        String email = principal.getName(); 
        Store store = storeService.getStoreById(storeId);

        if (store == null) {
            return ResponseEntity.notFound().build();
        }

        // Check if the authenticated user owns the store
        if (!store.getUser().getEmail().equals(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(store);
    }

    @PostMapping
    public ResponseEntity<String> addStore(@RequestBody Store store, Principal principal) {
    
        String email = principal.getName();
        User user = userService.getUserByEmail(email);

        if (store.getName() == null || store.getName().isEmpty()) {
            return ResponseEntity.badRequest().body("Store name cannot be empty.");
        }
        
        store.setUser(user);
        storeService.createStore(store);

        return ResponseEntity.ok("Store created successfully");
        
    }

    @DeleteMapping("/{storeId}")
    public ResponseEntity<?> deleteStoreById(@PathVariable Long storeId, Principal principal) {
        String email = principal.getName();
        Store store = storeService.getStoreById(storeId);
        
        if (store == null) {
            return ResponseEntity.notFound().build();
        }

        // Check if the authenticated user owns the store
        if (!store.getUser().getEmail().equals(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // If the user owns the store, delete it
        storeService.deleteStoreById(storeId);
        return ResponseEntity.ok("Store deleted successfully");
    }

    @PutMapping("/{storeId}")
    public ResponseEntity<?> updateStore(@PathVariable Long storeId, @RequestBody Store updatedStore, Principal principal) {
        String email = principal.getName(); 
        Store store = storeService.getStoreById(storeId);

        if (store == null) {
            return ResponseEntity.notFound().build();
        }

        if (!store.getUser().getEmail().equals(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        if (updatedStore.getName() != null && !updatedStore.getName().trim().isEmpty()) {
            store.setName(updatedStore.getName());
            Store updated = storeService.createStore(store);
            
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.badRequest().body("Store name cannot be empty.");
        }
    }
}
