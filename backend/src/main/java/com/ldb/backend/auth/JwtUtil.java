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

    /*
     * JWT Creation Process:
     * Step 1 - Header Creation (type and alg)
     * Step 2 - Payload Creation (claims)
     * Step 3 - Encoding (Base64Url-encode the header and the payload separately to create two segments)
     * Step 4 - Signature Creation (combine header, payload and secret key)
     * Step 5 - JWT assembly
     * Step 6 - Final JWT
     * Example: 
     *      Header: eyJhbGciOiAiSFMyNTYiLCAidHlwIjogIkpXVCJ9
            Payload: eyJzdWIiOiAiMTIzNDU2Nzg5MCIsICJuYW1lIjogIkpvaG4gRG9lIiwgImlhdCI6IDE1MTYyMzkwMjJ9
            Signature: QYwZgQOymO4tF7Zl_3eibnJ7rbbHjfWW9-_PnvkvDyQ
            JWT: eyJhbGciOiAiSFMyNTYiLCAidHlwIjogIkpXVCJ9.eyJzdWIiOiAiMTIzNDU2Nzg5MCIsICJuYW1lIjogIkpvaG4gRG9lIiwgImlhdCI6IDE1MTYyMzkwMjJ9.QYwZgQOymO4tF7Zl_3eibnJ7rbbHjfWW9-_PnvkvDyQ
     */

    // {
    //     "typ": "JWT",
    //     "alg": "HS256"
    // }

    // {
    //     "sub": "123",
    //     "iat": 1697448625,
    //     "exp": 1697450425
    // }

    // HMACSHA256(
    //     base64UrlEncode(header) + "." +
    //     base64UrlEncode(payload),
        
    //     your-256-bit-secret

    // )
      
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437"; 
  
    public String createToken(long userId) { 
        String userIdString = String.valueOf(userId); // Cast to string

        return Jwts.builder() 
                .setHeaderParam("typ","JWT")
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
        try {
            Date expirationDate = extractExpirationDate(token);
            Date currentDate = new Date(System.currentTimeMillis());

            return (expirationDate != null && currentDate.before(expirationDate));
        } catch (Exception e) {
            return false;
        } 
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
