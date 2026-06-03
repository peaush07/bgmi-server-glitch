package com.bgmi.dto;

import lombok.Data;

@Data
public class StartServerRequest {
    private String serverName;
    private String season;
    private String accessType; // FREE_TRIAL, PREMIUM, SEASONAL
}
