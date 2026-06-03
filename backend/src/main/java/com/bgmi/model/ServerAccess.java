package com.bgmi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "server_access")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String serverName;

    @Column(nullable = false)
    private Long accessDurationMillis; // Duration in milliseconds

    @Enumerated(EnumType.STRING)
    private AccessType accessType = AccessType.FREE_TRIAL;

    @Enumerated(EnumType.STRING)
    private AccessStatus status = AccessStatus.ACTIVE;

    private LocalDateTime startedAt = LocalDateTime.now();

    private LocalDateTime expiresAt;

    private LocalDateTime createdAt = LocalDateTime.now();

    public enum AccessType {
        FREE_TRIAL, PREMIUM, SEASONAL
    }

    public enum AccessStatus {
        ACTIVE, EXPIRED, STOPPED
    }
}
