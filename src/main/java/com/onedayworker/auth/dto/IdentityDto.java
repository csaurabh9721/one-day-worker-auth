package com.onedayworker.auth.dto;

import com.onedayworker.auth.util.enums.AccountStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class IdentityDto extends BaseEntityDto {

    private String email;
    private String phone;
    private String password;
    private AccountStatus status;
    private Boolean emailVerified;
    private Boolean phoneVerified;
    private Integer failedLoginAttempts;
    private LocalDateTime accountLockedUntil;
    private LocalDateTime lastLoginAt;
}
