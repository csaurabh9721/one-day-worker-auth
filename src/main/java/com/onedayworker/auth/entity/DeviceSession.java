package com.onedayworker.auth.entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "device_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceSession extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "identity_id")
    private Identity identity;

    private String deviceId;

    private String deviceName;

    private String operatingSystem;

    private String browser;

    private String ipAddress;

    private LocalDateTime lastSeenAt;

    private Boolean active = true;

    @OneToOne(mappedBy = "deviceSession",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private RefreshToken refreshToken;

}