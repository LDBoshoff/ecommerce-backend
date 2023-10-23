package com.ldb.backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ldb.backend.auth.JwtUtil;
import com.ldb.backend.model.Role;
import com.ldb.backend.model.User;
import com.ldb.backend.model.dto.LoginRequest;
import com.ldb.backend.model.dto.RegisterRequest;
import com.ldb.backend.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
    
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        try {
            if (!registerRequest.isValid()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email, password, first name, and last name are required.");
            }

            if (userService.emailExists(registerRequest.getEmail())) { 
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists"); // Change status code and message to not leak user info to bad actors
            }

            String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());
            User user = new User(registerRequest.getEmail(), encodedPassword, registerRequest.getFirstName(), registerRequest.getLastName(), Role.USER);
            userService.createUser(user);

            return ResponseEntity.ok("Registration successful"); 
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed due to an error.");     
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            if (!loginRequest.isValid()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email and password are required.");
            }
            
            // Step 1: Create an authentication object
            Authentication authToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()); // email = principal, password = credential
    
            // Step 2: Authenticate the user
            authenticationManager.authenticate(authToken);

            // Step 3: Retrieve the user with login request details
            User user = userService.getUserByEmail(loginRequest.getEmail());
            
            if (user == null) {
                // Handle user not found (authentication failure)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found.");
            }

            // Step 4: Generate a JWT token and login response
            String token = jwtUtil.createToken(user);

            // Step 5: create response
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("userId", user.getId());

            // Step 6: Send the response
            return ResponseEntity.ok(response);
    
        } catch (AuthenticationException e) {
            // Handle authentication failure
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed.");
        } catch (Exception e) {
            // Handle any other potential exceptions (e.g., database errors)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login Error.");
        }
    }


  /*
   * Resouce Access:
        1) The process starts when the user sends a request to the Service. The request is first intercepted by JwtAuthenticationFilter, 
            which is a custom filter integrated into the SecurityFilterChain.
        2) As the API is secured, if the JWT is missing, a response with HTTP Status 403 is sent to the user.
        3) When an existing JWT is received, JwtService is called to extract the userEmail from the JWT. If the userEmail cannot be extracted, 
            a response with HTTP Status 403 is sent to the user.
        4) If the userEmail can be extracted, it will be used to query the user’s authentication and authorization information via UserDetailsService.
        5) If the user’s authentication and authorization information does not exist in the database, a response with HTTP Status 403 is sent to the user.
        6) If the JWT is expired, a response with HTTP Status 403 is sent to the user.
        7) Upon successful authentication, the user’s details are encapsulated in a UsernamePasswordAuthenticationToken object 
            and stored in the SecurityContextHolder.
        8) The Spring Security Authorization process is automatically invoked.
        9) The request is dispatched to the controller, and a successful JSON response is returned to the user.

    1. SecurityFilterChain: a filter chain which is capable of being matched against an HttpServletRequest. in order to decide whether it applies to that request.
    2. SecurityContextHolder: is where Spring Security stores the details of who is authenticated. Spring Security uses that information for authorization.
    3. UserDetailsService: Service to fetch user-specific data.
    4. Authorization Architecture
   */


    
    


}

