package com.onedayworker.auth.entity;

import com.onedayworker.auth.util.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "identities",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "phone")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Identity extends BaseEntity {

    @Column(length = 100)
    private String email;

    @Column(length = 20,nullable = false)
    private String phone;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    @Column(nullable = false)
    private Boolean phoneVerified = false;

    private Integer failedLoginAttempts = 0;

    private LocalDateTime accountLockedUntil;

    private LocalDateTime lastLoginAt;

}
