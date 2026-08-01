package com.campus.lostfound.util;

import com.campus.lostfound.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey signingKey;
    private final long expiration;

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
        this.expiration = expiration;
    }

    public String generateToken(User user) {
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(user.getStudentNo())
                .claim("userId", user.getId())
                .claim("username", user.getUsername())
                .claim("role", user.getRole())
                .issuedAt(now)
                .expiration(expirationTime)
                .signWith(signingKey)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public String getStudentNo(String token) {
        return parseToken(token).getSubject();
    }

    public Long getUserId(String token) {
        return parseToken(token)
                .get("userId", Long.class);
    }

    public String getRole(String token) {
        return parseToken(token)
                .get("role", String.class);
    }
}