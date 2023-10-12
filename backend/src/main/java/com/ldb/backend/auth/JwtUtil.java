package com.ldb.backend.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component; 
import java.security.Key;
import java.util.Date;
import java.util.function.Function;
  
@Component
public class JwtUtil { 

    // // To be sent in the response header after successfull login: 
    // // HTTP/1.1 200 OK
    // // Content-Type: application/json
    // // Authorization: Bearer <JWT-Token>

    // // On the client side, the JWT can be extracted from the "Authorization" header and stored securely (e.g., in an HTTP-only cookie or local storage) 
    // // for subsequent requests to protected endpoints. The client includes the token in the "Authorization" header for authentication when accessing those protected resources.
    // public String generateToken(Long userId) { 
    //     // Map<String, Object> claims = new HashMap<>(); 
    //     // return createToken(claims, userName); 

    //     return createToken(userId); // add claims later
    // } 
  
    // // userId used for JWT 
    // // Example :
    // // {
    // //      "sub": "1",
    // //      "iat": 1634101203,       // Example timestamp for the issued at claim
    // //      "exp": 1634104803        // Example timestamp for the expiration claim (1 hour later)
    // // }
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437"; 
  
    public String createToken(long userId) { 
        String userIdString = String.valueOf(userId); // Cast to string

        return Jwts.builder() 
                // .setClaims(claims) 
                .setSubject(userIdString) 
                .setIssuedAt(new Date(System.currentTimeMillis())) 
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) 
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact(); 
    } 
  
    private Key getSignKey() { 
        byte[] keyBytes= Decoders.BASE64.decode(SECRET); 
        return Keys.hmacShaKeyFor(keyBytes); 
    } 

    public Long extractUserId(String token) {
        String userId = extractClaim(token, Claims::getSubject);
        // Add validation here to check if userId is a valid long.
        // If it's not valid, return an appropriate value or throw exception.
        return Long.parseLong(userId);
    }
  
    public Date extractExpirationDate(String token) { 
        return extractClaim(token, Claims::getExpiration); 
    } 
  
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) { 
        final Claims claims = extractAllClaims(token); 
        return claimsResolver.apply(claims); 
    } 
  
    private Claims extractAllClaims(String token) { 
        return Jwts 
                .parserBuilder() 
                .setSigningKey(getSignKey()) 
                .build() 
                .parseClaimsJws(token) 
                .getBody(); 
    } 
  
    public boolean isTokenValid(String token) {
        Date expirationDate = extractExpirationDate(token);
    
        if (expirationDate != null) {
            Date currentDate = new Date(System.currentTimeMillis());
            return currentDate.before(expirationDate); // Token is valid if current date is after the expiration date
        }
    
        // If there is no expiration date, you may choose to return true or false based on your requirements
        return false;
    }
  
    // users need include their userId along with the JWT token - eiher as request parameter, or custom header or body
    // implementation idea = send store id in request, get userId from storeId and use that as parameter
    public boolean validateToken(String token, Long userId) {
        if (!isTokenValid(token)) {
            return false;
        }
        
        Long tokenUserId = extractUserId(token); // Extract user ID from the token's claims
        
        return tokenUserId != null && tokenUserId.equals(userId); // Compare the extracted user ID with the provided user ID
  
    }

} 
