package com.onedayworker.auth.controller;

import com.onedayworker.auth.dto.RefreshTokenDto;
import com.onedayworker.auth.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/refresh-tokens")
@RequiredArgsConstructor
public class RefreshTokenController {

    private final RefreshTokenService service;

    @GetMapping
    public List<RefreshTokenDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public RefreshTokenDto findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<RefreshTokenDto> create(@RequestBody RefreshTokenDto dto) {
        RefreshTokenDto created = service.create(dto);
        return ResponseEntity.created(URI.create("/refresh-tokens/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public RefreshTokenDto update(@PathVariable UUID id, @RequestBody RefreshTokenDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
