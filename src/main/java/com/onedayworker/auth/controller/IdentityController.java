package com.onedayworker.auth.controller;

import com.onedayworker.auth.dto.IdentityDto;
import com.onedayworker.auth.service.IdentityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/identities")
@RequiredArgsConstructor
public class IdentityController {

    private final IdentityService service;

    @GetMapping
    public List<IdentityDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public IdentityDto findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<IdentityDto> create(@RequestBody IdentityDto dto) {
        IdentityDto created = service.create(dto);
        return ResponseEntity.created(URI.create("/identities/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public IdentityDto update(@PathVariable UUID id, @RequestBody IdentityDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
