package com.ldb.backend.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component; 
import java.security.Key;
import java.util.Date;
import java.util.function.Function;
  
@Component
public class JwtUtil { 

    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437"; 

    public String createToken(UserDetails userDetails) { 

        return Jwts.builder() 
                .setHeaderParam("typ","JWT")
                // .setClaims(claims) 

                .setSubject(userDetails.getUsername()) // Use username as the subject
                .setIssuedAt(new Date(System.currentTimeMillis())) 
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) 
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact(); 
    } 
  
    private Key getSignKey() { 
        byte[] keyBytes= Decoders.BASE64.decode(SECRET); 
        return Keys.hmacShaKeyFor(keyBytes); 
    } 

    public String extractEmail(String token) {
        String email = extractClaim(token, Claims::getSubject);
        return email;
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
  
    public boolean validToken(String token) {
        Date currentDate = new Date(System.currentTimeMillis());
        
        return currentDate.before(extractExpirationDate(token)); 
    }
  
    public Boolean validateToken(String token, UserDetails userDetails) { 
        String email = extractEmail(token); 
        
        return (email.equals(userDetails.getUsername()) && validToken(token)); 
    } 

} 
