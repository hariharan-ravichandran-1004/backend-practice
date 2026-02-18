package com.backend.java.utils;

import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    private final String SECRET_KEY = "7bcf9b8a3e1d4c5a9b8f2e7d1c0b3a4f5e6d7c8b9a0f1e2d3c4b5a6f7e8d9c0b"; // 256-bit secret
    private final long EXPIRATION_MS = 60000; // 1 hour

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Generate JWT
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)                // Store username in payload
                .setIssuedAt(new Date())             // Token issued now
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS)) // Expire in 1 hour
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Sign
                .compact();
    }

    // Extract username from JWT
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Validate token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token); // Throws exception if invalid
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}

