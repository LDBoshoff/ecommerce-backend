package com.ldb.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    void shouldGetUserById() {
        // (1, 'john.doe@example.com', 'password1', 'John', 'Doe'),
        User retrievedUser = userService.getUserById(1L);

        assertNotNull(retrievedUser);

        assertEquals("john.doe@example.com", retrievedUser.getEmail());
        assertEquals("password1", retrievedUser.getPassword());
        assertEquals("John", retrievedUser.getFirstName());
        assertEquals("Doe", retrievedUser.getLastName());

        // (2, 'alice.smith@example.com', 'password2', 'Alice', 'Smith')
        User retrievedUser2 = userService.getUserById(2L);

        assertNotNull(retrievedUser);

        assertNotEquals("john.doe@example.com", retrievedUser2.getEmail());
        assertNotEquals("password1", retrievedUser2.getPassword());
        assertNotEquals("John", retrievedUser2.getFirstName());
        assertNotEquals("Doe", retrievedUser2.getLastName());
    }

    @Test
    @Rollback
    public void testUniqueEmail() {
        // Test a unique email
        assertTrue(userService.uniqueEmail("unique@example.com"));

        // Save a user with the same email
        User existingUser = new User("unique@example.com", "password", "Existing", "User");
        userService.createUser(existingUser);
        
        // Test a non-unique email
        assertFalse(userService.uniqueEmail("unique@example.com"));
        assertFalse(userService.uniqueEmail("john.doe@example.com"));
    }

    @Test
    @Rollback
    public void testGetUserByEmail() {
        // Retrieve the user by email
        User retrievedUser = userService.getUserByEmail("john.doe@example.com");

        assertNotNull(retrievedUser);
        assertEquals("john.doe@example.com", retrievedUser.getEmail());
        assertNotEquals("alice.smith@example.com", retrievedUser.getEmail());

        User nonExistingUser = userService.getUserByEmail("test@example.com");

        assertNull(nonExistingUser);
    }

    @Test
    @Rollback
    public void testSuccessfulUserRegistration() {
        User userToCreate = new User("test@example.com", "password", "Test", "User");

        // Attempt to register the user
        boolean isRegistered = userService.registeredUser(userToCreate);

        // Verify successful registration
        assertTrue(isRegistered);
        User createdUser = userService.getUserByEmail("test@example.com");
        assertNotNull(createdUser);
        assertEquals("test@example.com", createdUser.getEmail());
        assertNotEquals("john.doe@example", createdUser.getEmail());
        // Add more assertions for other properties as needed
    }

    @Test
    @Rollback
    public void testDuplicateEmailRegistration() {
        // Save a user with the same email
        User existingUser = new User("test@example.com", "password", "Existing", "User");
        userService.createUser(existingUser);

        // Attempt to register a user with the same email
        User duplicateUser = new User("test@example.com", "newpassword", "New", "User");

        // Attempt to register the duplicate user
        boolean isRegistered = userService.registeredUser(duplicateUser);

        // Verify that registration fails due to duplicate email
        assertFalse(isRegistered);
        User newUser = userService.getUserByEmail("test@example.com");
        assertNotNull(newUser);
        // Ensure that the original user data remains unchanged
        assertEquals("password", newUser.getPassword());
        assertEquals("Existing", newUser.getFirstName());
        assertEquals("User", newUser.getLastName());
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