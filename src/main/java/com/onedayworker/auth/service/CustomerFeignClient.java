package com.onedayworker.auth.service;

import com.onedayworker.auth.dto.CustomerRegistrationRequest;
import com.onedayworker.auth.dto.CustomerRegistrationResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "Customer-Service"
)
public interface CustomerFeignClient {

    @PostMapping("/customerService/customer/customerRegister")
    CustomerRegistrationResponseDto createCustomer(
            @RequestBody CustomerRegistrationRequest request
    );

}
