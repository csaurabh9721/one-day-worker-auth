package com.onedayworker.auth.controller;

import com.onedayworker.auth.dto.AuthDtos;
import com.onedayworker.auth.service.OtpService;
import com.onedayworker.auth.util.APIBaseRoute;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(APIBaseRoute.API_VERSION +"/otp")
@RequiredArgsConstructor
public class OtpController {

    private final OtpService service;

    @PostMapping("/send")
    public ResponseEntity<Void> send(@RequestBody AuthDtos.OtpSendRequest request) {
        service.sendOtp(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/verify")
    public ResponseEntity<Void> verify(@RequestBody AuthDtos.OtpVerifyRequest request) {
        service.verifyOtp(request);
        return ResponseEntity.noContent().build();
    }
}
