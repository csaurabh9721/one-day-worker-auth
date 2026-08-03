package com.onedayworker.auth.service;

import com.onedayworker.auth.dto.CustomerRegistrationRequest;
import com.onedayworker.auth.dto.WorkerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "Worker-Service"
)
public interface WorkerFeignClient {
    @PostMapping("/workerService/api/v1/workers")
    WorkerResponse createWorker(
            @RequestBody CustomerRegistrationRequest request
    );
}
