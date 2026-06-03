package com.bgmi.dto;

import lombok.Data;

@Data
public class GenerateKeyRequest {
    private Long userId;
    private String keyName;
    private String season;
    private Long expirationDays;
}
