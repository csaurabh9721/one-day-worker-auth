package com.onedayworker.auth.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceSessionDto extends BaseEntityDto {

    private Long identityId;
    private String deviceId;
    private String deviceName;
    private String operatingSystem;
    private String browser;
    private String ipAddress;
    private LocalDateTime lastSeenAt;
    private Boolean active;
}
