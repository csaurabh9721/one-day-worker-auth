package com.onedayworker.auth.dto;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRegistrationResponseDto {

    private Long id;

    private Long identityId;

    private String firstName;

    private String lastName;

    private String phone;

    private String gender;

    private LocalDate dob;

    private String profileImage;

    private Boolean active ;

}
