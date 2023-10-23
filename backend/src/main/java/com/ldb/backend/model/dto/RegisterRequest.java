package com.ldb.backend.model.dto;

public class RegisterRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    // Getters and setters for the fields

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isValid() {
        return email != null && !email.isEmpty() &&
               password != null && !password.isEmpty() &&
               firstName != null && !firstName.isEmpty() &&
               lastName != null && !lastName.isEmpty();
    }
    
}