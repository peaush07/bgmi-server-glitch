package com.bgmi.controller;

import com.bgmi.dto.GenerateKeyRequest;
import com.bgmi.dto.ApiKeyResponse;
import com.bgmi.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/generate-key")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiKeyResponse> generateApiKey(@RequestBody GenerateKeyRequest request) {
        return ResponseEntity.ok(adminService.generateApiKey(request));
    }

    @GetMapping("/users/{userId}/keys")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getUserKeys(@PathVariable Long userId) {
        return ResponseEntity.ok(adminService.getUserApiKeys(userId));
    }
}
