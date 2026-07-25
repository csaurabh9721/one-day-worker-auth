package com.onedayworker.auth.controller;

import com.onedayworker.auth.dto.IdentityRoleDto;
import com.onedayworker.auth.service.IdentityRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/identity-roles")
@RequiredArgsConstructor
public class IdentityRoleController {

    private final IdentityRoleService service;

    @GetMapping
    public List<IdentityRoleDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public IdentityRoleDto findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<IdentityRoleDto> create(@RequestBody IdentityRoleDto dto) {
        IdentityRoleDto created = service.create(dto);
        return ResponseEntity.created(URI.create("/identity-roles/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public IdentityRoleDto update(@PathVariable UUID id, @RequestBody IdentityRoleDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
