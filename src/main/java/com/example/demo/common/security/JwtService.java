package com.example.demo.common.security;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import java.util.Date;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import javax.crypto.SecretKey;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;    
    @Value("${jwt.expiration}")
    private long expiration;

    public String generateToken(String username) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(key)
            .compact();
     
    }
}