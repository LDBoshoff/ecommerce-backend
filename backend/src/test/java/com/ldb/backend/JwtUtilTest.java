package com.ldb.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Date; 

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ldb.backend.auth.JwtUtil;

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
}