package com.bgmi.controller;

import com.bgmi.security.JwtUtil;
import com.bgmi.security.JwtUtil.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // Optional: require admin password to issue tokens.
    @Value("${app.require-admin-for-token:true}")
    private boolean requireAdminForToken;

    @PostMapping("/create-session")
    public ResponseEntity<?> createSession(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String adminPassword = body.get("adminPassword");

        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "email is required"));
        }

        if (requireAdminForToken) {
            // Basic admin password check: read current admin password from env or replace with DB lookup.
            String expected = System.getenv("ADMIN_PASSWORD");
            if (expected == null || !expected.equals(adminPassword)) {
                return ResponseEntity.status(401).body(Map.of("error", "invalid admin password"));
            }
        }

        Token token = JwtUtil.createToken(email);
        return ResponseEntity.ok(Map.of("token", token.token, "expiresAt", token.expiresAt));
    }
}
