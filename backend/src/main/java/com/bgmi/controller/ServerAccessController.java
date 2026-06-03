package com.bgmi.controller;

import com.bgmi.dto.StartServerRequest;
import com.bgmi.dto.ServerAccessResponse;
import com.bgmi.service.ServerAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ServerAccessController {

    private final ServerAccessService serverAccessService;

    @PostMapping("/start")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ServerAccessResponse> startServer(@RequestBody StartServerRequest request) {
        return ResponseEntity.ok(serverAccessService.startServerAccess(request));
    }

    @GetMapping("/active")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getActiveAccess() {
        return ResponseEntity.ok(serverAccessService.getActiveAccess());
    }

    @PostMapping("/{accessId}/stop")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> stopServer(@PathVariable Long accessId) {
        return ResponseEntity.ok(serverAccessService.stopServerAccess(accessId));
    }
}
