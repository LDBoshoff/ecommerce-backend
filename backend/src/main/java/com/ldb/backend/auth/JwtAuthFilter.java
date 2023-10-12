// package com.ldb.backend.auth;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import io.jsonwebtoken.Claims;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.security.authentication.AuthenticationServiceException;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;

// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.HashMap;
// import java.util.Map;

// @Component
// public class JwtAuthFilter extends OncePerRequestFilter {


//     @Autowired
//     private JwtUtil jwtUtil;

//     @Autowired
//     private ObjectMapper mapper; // why not private final?

//     // public JwtAuthorizationFilter(JwtUtil jwtUtil, ObjectMapper mapper) {
//     //     this.jwtUtil = jwtUtil;
//     //     this.mapper = mapper;
//     // }

//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//         Map<String, Object> errorDetails = new HashMap<>();

//         try {
//             String accessToken = jwtUtil.resolveToken(request);
//             if (accessToken == null ) {
//                 filterChain.doFilter(request, response);
//                 return;
//             }
//             System.out.println("token : "+accessToken);
//             Claims claims = jwtUtil.resolveClaims(request);

//             if(claims != null & jwtUtil.validateClaims(claims)){
//                 String email = claims.getSubject();
//                 System.out.println("email : "+email);
//                 Authentication authentication =
//                         new UsernamePasswordAuthenticationToken(email,"",new ArrayList<>());
//                 SecurityContextHolder.getContext().setAuthentication(authentication);
//             }

//         }catch (Exception e){
//             errorDetails.put("message", "Authentication Error");
//             errorDetails.put("details",e.getMessage());
//             response.setStatus(HttpStatus.FORBIDDEN.value());
//             response.setContentType(MediaType.APPLICATION_JSON_VALUE);

//             mapper.writeValue(response.getWriter(), errorDetails);

//         }
//         filterChain.doFilter(request, response);
//     }
// }


// import com.ey.springboot3security.service.JwtService; 
// import com.ey.springboot3security.service.UserInfoService; 
// import jakarta.servlet.FilterChain; 
// import jakarta.servlet.ServletException; 
// import jakarta.servlet.http.HttpServletRequest; 
// import jakarta.servlet.http.HttpServletResponse; 
// import org.springframework.beans.factory.annotation.Autowired; 
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; 
// import org.springframework.security.core.context.SecurityContextHolder; 
// import org.springframework.security.core.userdetails.UserDetails; 
// import org.springframework.security.web.authentication.WebAuthenticationDetailsSource; 
// import org.springframework.stereotype.Component; 
// import org.springframework.web.filter.OncePerRequestFilter; 
  
// import java.io.IOException; 
  
// // This class helps us to validate the generated jwt token 
// @Component
// public class JwtAuthFilter extends OncePerRequestFilter { 

//     // Create a custom filter (extending OncePerRequestFilter) that intercepts incoming requests to validate the JWT token.
//     // In the doFilterInternal method, extract the token from the request header, validate it, and set up the security context if the token is valid.
  
//     @Autowired
//     private JwtService jwtService; 
  
//     @Autowired
//     private UserInfoService userDetailsService; 
  
//     @Override
//     protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException { 
//         String authHeader = request.getHeader("Authorization"); 
//         String token = null; 
//         String username = null; 
//         if (authHeader != null && authHeader.startsWith("Bearer ")) { 
//             token = authHeader.substring(7); 
//             username = jwtService.extractUsername(token); 
//         } 
  
//         if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) { 
//             UserDetails userDetails = userDetailsService.loadUserByUsername(username); 
//             if (jwtService.validateToken(token, userDetails)) { 
//                 UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()); 
//                 authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); 
//                 SecurityContextHolder.getContext().setAuthentication(authToken); 
//             } 
//         } 
//         filterChain.doFilter(request, response); 
//     } 
// } 

