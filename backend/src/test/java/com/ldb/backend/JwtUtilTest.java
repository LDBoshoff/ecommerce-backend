package com.ldb.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date; 

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import com.ldb.backend.auth.JwtUtil;
import com.ldb.backend.service.UserService;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private UserService userService;
    
    @Test
    public void testCreateToken() {
        UserDetails user = userService.loadUserByUsername("luhahn.boshoff@gmail.com");
        String token = jwtUtil.createToken(user);
        assertNotNull(token);
    }

    @Test
    public void testExtractEmail() {
        String email = "luhahn.boshoff@gmail.com";
        UserDetails user = userService.loadUserByUsername(email);
        String token = jwtUtil.createToken(user);

        String extractedEmail = jwtUtil.extractEmail(token);
        assertEquals(email, extractedEmail);
    }

    @Test
    public void testExtractExpirationDate() {
        String email = "luhahn.boshoff@gmail.com";
        UserDetails user = userService.loadUserByUsername(email);
        String token = jwtUtil.createToken(user);

        Date expirationDate = jwtUtil.extractExpirationDate(token);
        assertNotNull(expirationDate);
    }
    
    @Test
    public void testValidateToken() {
        String email = "luhahn.boshoff@gmail.com";
        UserDetails user = userService.loadUserByUsername(email);
        String token = jwtUtil.createToken(user);

        assertTrue(jwtUtil.validateToken(token, user));
    }

    @Test
    public void testTokenNonExpired() {
        String email = "luhahn.boshoff@gmail.com";
        UserDetails user = userService.loadUserByUsername(email);
        String token = jwtUtil.createToken(user);

        assertTrue(jwtUtil.validToken(token));
    }

    // @Test
    // public void testInvalidToken() {
    //     Long userId = 123L;
    //     String token = jwtUtil.createToken(userId);

    //     Long fakeUserId = 451L;
    //     assertFalse(jwtUtil.validateToken(token, fakeUserId));
    // }

    // @Test
    // public void testExpiredToken() throws  InterruptedException {
    //     Long userId = 123L;
    //     String userIdString = String.valueOf(userId);
    
    //     String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";        
    //     byte[] keyBytes = Decoders.BASE64.decode(SECRET);
    
    //     Key signKey = Keys.hmacShaKeyFor(keyBytes); 
    //     // Create a valid token with an expiration time set in the future
    //     String token = Jwts.builder() 
    //                     .setHeaderParam("typ","JWT")
    //                     // .setClaims(claims) 
    //                     .setSubject(userIdString) 
    //                     .setIssuedAt(new Date(System.currentTimeMillis())) 
    //                     .setExpiration(new Date(System.currentTimeMillis() + 500)) // Experation date 0.5 seconds from creation
    //                     .signWith(signKey, SignatureAlgorithm.HS256)
    //                     .compact(); 

    //     Thread.sleep(1500); // Wait for 1.5 seconds

    //     assertFalse(jwtUtil.isTokenValid(token));
    //     System.out.println("Token = " + token);
    // }
}
