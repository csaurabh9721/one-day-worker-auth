package com.onedayworker.auth.dto;



import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRegistrationRequest {


       private    Long identityId;

       private   String firstName;

       private   String lastName;

       private   String phone;

       private   String gender;

       private   LocalDate dob;


}
