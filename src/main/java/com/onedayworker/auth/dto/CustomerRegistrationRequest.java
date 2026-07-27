package com.onedayworker.auth.dto;



import lombok.*;

import java.time.LocalDate;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRegistrationRequest {


       private    UUID identityId;

       private   String firstName;

       private   String lastName;

       private   String phone;

       private   String gender;

       private   LocalDate dob;


}
