package com.onedayworker.auth.dto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequestDto {

    @NotNull
    private String phone;

    @NotNull
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;
}
