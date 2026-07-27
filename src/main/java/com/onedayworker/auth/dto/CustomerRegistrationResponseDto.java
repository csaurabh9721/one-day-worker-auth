package com.onedayworker.auth.dto;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRegistrationResponseDto {

    private UUID id;

    private UUID identityId;

    private String firstName;

    private String lastName;

    private String phone;

    private String gender;

    private LocalDate dob;

    private String profileImage;

    private Boolean active ;

}
