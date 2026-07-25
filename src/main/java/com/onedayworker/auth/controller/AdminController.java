package com.onedayworker.auth.controller;

import com.onedayworker.auth.dto.AuthDtos;
import com.onedayworker.auth.dto.IdentityDto;
import com.onedayworker.auth.dto.IdentityRoleDto;
import com.onedayworker.auth.service.DeviceSessionService;
import com.onedayworker.auth.service.IdentityRoleService;
import com.onedayworker.auth.service.IdentityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final IdentityService identityService;
    private final DeviceSessionService deviceSessionService;
    private final IdentityRoleService identityRoleService;

    @GetMapping("/identities")
    public List<IdentityDto> findAllIdentities() {
        return identityService.findAll();
    }

    @GetMapping("/identities/{id}")
    public IdentityDto findIdentityById(@PathVariable UUID id) {
        return identityService.findById(id);
    }

    @PatchMapping("/block")
    public IdentityDto block(@RequestBody AuthDtos.AdminIdentityRequest request) {
        return identityService.block(request.identityId());
    }

    @PatchMapping("/unblock")
    public IdentityDto unblock(@RequestBody AuthDtos.AdminIdentityRequest request) {
        return identityService.unblock(request.identityId());
    }

    @PatchMapping("/roles")
    public IdentityRoleDto assignRole(@RequestBody AuthDtos.AdminRoleRequest request) {
        return identityRoleService.assignRole(request.identityId(), request.roleName());
    }

    @DeleteMapping("/sessions")
    public ResponseEntity<Void> deleteAllSessions() {
        deviceSessionService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
