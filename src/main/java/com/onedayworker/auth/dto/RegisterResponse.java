package com.onedayworker.auth.dto;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {
    private UUID uuid;
    private String email;
    private String name;
    private String phone;
    private String role;
    private String status;
    private String message;
    private String accessToken;
    private String refreshToken;
}
