package com.bgmi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ServerAccessResponse {
    private Long id;
    private String serverName;
    private Long remainingTimeMillis;
    private LocalDateTime expiresAt;
    private String status;
    private String message;
}
