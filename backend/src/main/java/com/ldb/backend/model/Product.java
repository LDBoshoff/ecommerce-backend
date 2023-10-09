package com.ldb.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity  // Annotate the class as an entity for JPA (Java Persistence API)
@Table(name = "products")  // Specify the name of the table in the database
public class Product {
    
    @Id  // Mark the 'id' attribute as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Use an auto-generated value for 'id' using identity strategy
    private Long id;
    
    @Column(nullable = false)  // Define 'name' attribute for product name, not nullable
    private String name;
    
    @Column(nullable = false)  // Define 'description' attribute for product description, not nullable
    private String description;
    
    @Column(nullable = false)  // Define 'price' attribute for product price, not nullable
    private double price;
    
    @Column(nullable = false)  // Define 'stockQuantity' attribute for product stock quantity, not nullable
    private int quantity;

    // Add a reference to the owning Store
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false) // Define the foreign key column
    private Store store;

    protected Product() {
        // Default constructor
    }

    public Product(String name, String description, double price, int quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
