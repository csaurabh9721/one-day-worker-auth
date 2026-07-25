package com.onedayworker.auth.entity;
import com.onedayworker.auth.util.enums.OtpType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "otps")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Otp extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "identity_id")
    private Identity identity;

    @Column(nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    private OtpType type;

    private Integer attempts = 0;

    private Boolean used = false;

    private LocalDateTime expiresAt;

}