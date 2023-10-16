package com.ldb.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.Key;
import java.util.Date; 

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ldb.backend.auth.JwtUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;
    
    @Test
    public void testCreateToken() {
        Long userId = 123L;
        String token = jwtUtil.createToken(userId);
        assertNotNull(token);
    }

    @Test
    public void testExtractUserId() {
        Long userId = 123L;
        String token = jwtUtil.createToken(userId);

        Long extractedUserId = jwtUtil.extractUserId(token);
        assertEquals(userId, extractedUserId);
    }

    @Test
    public void testExtractExpirationDate() {
        Long userId = 123L;
        String token = jwtUtil.createToken(userId);

        Date expirationDate = jwtUtil.extractExpirationDate(token);
        assertNotNull(expirationDate);
    }

    @Test
    public void testValidateToken() {
        Long userId = 123L;
        String token = jwtUtil.createToken(userId);

        assertTrue(jwtUtil.validateToken(token, userId));
    }

    @Test
    public void testIsTokenValid() {
        Long userId = 123L;
        String token = jwtUtil.createToken(userId);

        assertTrue(jwtUtil.isTokenValid(token));
    }

    @Test
    public void testInvalidToken() {
        Long userId = 123L;
        String token = jwtUtil.createToken(userId);

        Long fakeUserId = 451L;
        assertFalse(jwtUtil.validateToken(token, fakeUserId));
    }

    @Test
    public void testExpiredToken() throws  InterruptedException {
        Long userId = 123L;
        String userIdString = String.valueOf(userId);
    
        String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";        
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
    
        Key signKey = Keys.hmacShaKeyFor(keyBytes); 
        // Create a valid token with an expiration time set in the future
        String token = Jwts.builder() 
                        .setHeaderParam("typ","JWT")
                        // .setClaims(claims) 
                        .setSubject(userIdString) 
                        .setIssuedAt(new Date(System.currentTimeMillis())) 
                        .setExpiration(new Date(System.currentTimeMillis() + 500)) // Experation date 0.5 seconds from creation
                        .signWith(signKey, SignatureAlgorithm.HS256)
                        .compact(); 

        Thread.sleep(1500); // Wait for 1.5 seconds

        assertFalse(jwtUtil.isTokenValid(token));
        System.out.println("Token = " + token);
    }
}