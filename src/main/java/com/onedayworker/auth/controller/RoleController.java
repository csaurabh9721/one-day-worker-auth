package com.onedayworker.auth.controller;

import com.onedayworker.auth.dto.RoleDto;
import com.onedayworker.auth.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService service;

    @GetMapping
    public List<RoleDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public RoleDto findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<RoleDto> create(@RequestBody RoleDto dto) {
        RoleDto created = service.create(dto);
        return ResponseEntity.created(URI.create("/roles/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public RoleDto update(@PathVariable UUID id, @RequestBody RoleDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
