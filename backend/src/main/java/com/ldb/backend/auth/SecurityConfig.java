package com.ldb.backend.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.persistence.criteria.CriteriaBuilder.In;

@Configuration
public class SecurityConfig {
    // // In your SecurityConfig class, configure Spring Security to use your custom JwtAuthFilter within the security filter chain.

    // // Remember to configure Spring Security, particularly the permissions and roles required to access your protected endpoints. 
    // // This setup will ensure that your application correctly handles user authentication and authorization using JWTs.



    // // Define the security filter chain for configuring security rules.
    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //     http
    //         // Authorize HTTP requests using custom request matching.
    //         .authorizeHttpRequests(request -> request
    //             .requestMatchers("/api/register", "/api/login").permitAll() // Allow unauthenticated access to registration and login
    //             .anyRequest().authenticated() // Require authentication for all other requests
    //         )
    //         // Disable CSRF protection.
    //         .csrf(csrf -> csrf.disable())
    //         .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
           
    //     return http.build();  // Build and return the security filter chain.

    // }

    // // implement a jwtAuthenticationFilter() to process JWT tokens and extract user information. 
    // // Additionally, configure the issuance of JWT tokens when users successfully log in.
    // // The front end should store the received JWT and include it in the "Authorization" header for subsequent requests to protected endpoints. 
    // // Your server will validate the JWT and grant access if it's valid and the user has the required roles.
    // // Remember to implement proper JWT token validation and make sure to handle token expiration and refresh tokens if required for a seamless user experience.
    // @Bean
    // public JwtAuthFilter jwtAuthenticationFilter() {
    //     return new JwtAuthFilter();
    // }

    // // Define a bean for the password encoder (BCrypt in this case).
    // @Bean
    // public PasswordEncoder passwordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }

    // // Define a custom user details service for in-memory user management.
    // @Bean
    // public UserDetailsService testOnlyUsers(PasswordEncoder passwordEncoder) {
    //     User.UserBuilder users = User.builder();
    //     // Define user details for Sarah, Hank, and Kumar.
    //     UserDetails sarah = users
    //         .username("sarah1")
    //         .password(passwordEncoder.encode("abc123"))
    //         .roles("CARD-OWNER")
    //         .build();
    //     UserDetails hankOwnsNoCards = users
    //         .username("hank-owns-no-cards")
    //         .password(passwordEncoder.encode("qrs456"))
    //         .roles("NON-OWNER")
    //         .build();
    //     UserDetails kumar = users
    //         .username("kumar2")
    //         .password(passwordEncoder.encode("xyz789"))
    //         .roles("CARD-OWNER")
    //         .build();
    //     // Create and return an in-memory user details manager with the defined users.
    //     return new InMemoryUserDetailsManager(sarah, hankOwnsNoCards, kumar);
    // }
}

