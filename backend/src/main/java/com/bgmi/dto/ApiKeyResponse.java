package com.bgmi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiKeyResponse {
    private Long id;
    private String keyValue;
    private String keyName;
    private String season;
    private LocalDateTime expiresAt;
    private boolean active;
}
