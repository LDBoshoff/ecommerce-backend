package com.ldb.backend.model;

import jakarta.persistence.*;

@Entity // This annotation marks the class as an entity, making it eligible for JPA (Java Persistence API) mapping.
public class User {

    @Id // Specifies that the 'id' field is the primary key for this entity.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indicates that the primary key should be automatically generated (e.g., auto-incremented).
    private Long id;

    @Column(unique = true) // Defines the 'email' column in the database table, ensuring uniqueness.
    private String email; // Represents the user's email, which is used for authentication.

    private String password; // Stores the user's password securely.

    // private String roles; // Represents user roles, which can be used for role-based access control.

    // Default constructor
    public User() {
    }

    // Constructor with parameters to initialize 'email', 'password', and 'roles'.
    // add "String roles" as parameter
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getter for 'id'
    public Long getId() {
        return id;
    }

    // Setter for 'id'
    public void setId(Long id) {
        this.id = id;
    }

    // Getter for 'email'
    public String getEmail() {
        return email;
    }

    // Setter for 'email'
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter for 'password'
    public String getPassword() {
        return password;
    }

    // Setter for 'password'
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter for 'roles'
    // public String getRoles() {
    //     return roles;
    // }

    // Setter for 'roles'
    // public void setRoles(String roles) {
    //     this.roles = roles;
    // }
}
