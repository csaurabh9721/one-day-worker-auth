package com.onedayworker.auth.dto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {
    private Long id;
    private String email;
    private String name;
    private String phone;
    private String role;
    private String status;
    private String message;
    private String accessToken;
    private String refreshToken;
}
