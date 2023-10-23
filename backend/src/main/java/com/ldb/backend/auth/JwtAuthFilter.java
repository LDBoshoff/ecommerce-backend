package com.ldb.backend.auth;

import com.ldb.backend.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

  
// This class helps us to validate the generated jwt token 
@Component
public class JwtAuthFilter extends OncePerRequestFilter { 

    // Create a custom filter (extending OncePerRequestFilter) that intercepts incoming requests to validate the JWT token.
    // In the doFilterInternal method, extract the token from the request header, validate it, and set up the security context if the token is valid.
  
    @Autowired
    private JwtUtil jwtUtil; 
  
    @Autowired
    private UserService userService; 
  
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException { 

        String authHeader = request.getHeader("Authorization"); 
        String token = null; 
        String email = null; 
        
        if (authHeader == null || authHeader.isEmpty() || !authHeader.startsWith("Bearer ")) { 
            filterChain.doFilter(request, response);
            return;
        } 
        
        token = authHeader.substring(7); 
        email = jwtUtil.extractEmail(token); 

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) { 
            UserDetails userDetails = userService.loadUserByUsername(email); 
            
            if (jwtUtil.validateToken(token, userDetails)) { 
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); 
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); 
                SecurityContextHolder.getContext().setAuthentication(authToken); 
            } 
        } 
        filterChain.doFilter(request, response); 
    } 
} 

