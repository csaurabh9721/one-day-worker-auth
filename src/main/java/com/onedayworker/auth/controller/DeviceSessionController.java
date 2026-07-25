package com.onedayworker.auth.controller;

import com.onedayworker.auth.dto.DeviceSessionDto;
import com.onedayworker.auth.service.DeviceSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/device-sessions")
@RequiredArgsConstructor
public class DeviceSessionController {

    private final DeviceSessionService service;

    @GetMapping
    public List<DeviceSessionDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public DeviceSessionDto findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<DeviceSessionDto> create(@RequestBody DeviceSessionDto dto) {
        DeviceSessionDto created = service.create(dto);
        return ResponseEntity.created(URI.create("/device-sessions/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public DeviceSessionDto update(@PathVariable UUID id, @RequestBody DeviceSessionDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
