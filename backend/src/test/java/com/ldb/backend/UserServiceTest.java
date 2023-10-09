package com.ldb.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;

import com.ldb.backend.model.User;
import com.ldb.backend.service.UserService;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class UserServiceTest {
    
    @Autowired
    private UserService userService;

    @Test
    @Rollback
    public void shouldCreateANewUser() {
        User userToCreate = new User("test@example.com", "password", "Test", "User");

        // Call the createUser method
        User createdUser = userService.createUser(userToCreate);

        // Assert that the createdUser is not null
        assertNotNull(createdUser);
        // Add more assertions to check other properties of the createdUser
        assertEquals("test@example.com", createdUser.getEmail());
        assertEquals("password", createdUser.getPassword());
        assertEquals("Test", createdUser.getFirstName());
        assertEquals("User", createdUser.getLastName());
        // You can add more assertions for other properties as needed

        // Assert that the email, password, and names are not some fake values
        assertNotEquals("fake@example.com", createdUser.getEmail());
        assertNotEquals("fakepassword", createdUser.getPassword());
        assertNotEquals("Fake", createdUser.getFirstName());
        assertNotEquals("Fake", createdUser.getLastName());

        // Negative Assertion: Attempt to create a user with the same email should result in a DataIntegrityViolation
        User duplicateUser = new User("test@example.com", "newpassword", "New", "User");

        // Using assertThrows to catch the expected DataIntegrityViolationException
        assertThrows(DataIntegrityViolationException.class, () -> {
            userService.createUser(duplicateUser);
        }, "Duplicate entry");

    }

    @Test
    @Rollback
    public void shouldDeleteAUser() {
         // Create a user to be deleted
         User userToDelete = new User("test@example.com", "password", "Test", "User");
         User createdUser = userService.createUser(userToDelete);
 
         // Attempt to delete the user
         boolean deletionResult = userService.deleteUser(createdUser.getId());
 
         // Assert that the deletion was successful
         assertTrue(deletionResult);
 
         // Attempt to retrieve the user after deletion should return null
         User deletedUser = userService.getUserById(createdUser.getId());
         assertNull(deletedUser);
    }

    
}