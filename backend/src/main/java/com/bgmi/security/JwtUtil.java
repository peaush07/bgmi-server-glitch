package com.bgmi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

public final class JwtUtil {
    private static final String secretEnv = System.getenv("JWT_SECRET");
    private static final long ttlMs = Optional.ofNullable(System.getenv("JWT_TTL_MS"))
            .map(Long::parseLong)
            .orElse(3_600_000L); // default 1 hour

    private static final Key key = (secretEnv != null && !secretEnv.isBlank() && secretEnv.getBytes().length >= 32)
            ? Keys.hmacShaKeyFor(secretEnv.getBytes())
            : Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private JwtUtil() {}

    public static Token createToken(String email) {
        long now = System.currentTimeMillis();
        Date issuedAt = new Date(now);
        Date expiresAt = new Date(now + ttlMs);

        String token = Jwts.builder()
                .setSubject(email)
                .claim("email", email)
                .setIssuedAt(issuedAt)
                .setExpiration(expiresAt)
                .signWith(key)
                .compact();

        return new Token(token, expiresAt.getTime());
    }

    public static Claims validateToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public static class Token {
        public final String token;
        public final long expiresAt;

        public Token(String token, long expiresAt) {
            this.token = token;
            this.expiresAt = expiresAt;
        }
    }
}
