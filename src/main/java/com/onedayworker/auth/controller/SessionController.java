package com.onedayworker.auth.controller;

import com.onedayworker.auth.dto.DeviceSessionDto;
import com.onedayworker.auth.service.DeviceSessionService;
import com.onedayworker.auth.util.APIBaseRoute;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIBaseRoute.API_VERSION +"/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final DeviceSessionService service;

    @GetMapping
    public List<DeviceSessionDto> findAll() {
        return service.findAll();
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<Void> delete(@PathVariable Long sessionId) {
        service.delete(sessionId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        service.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
