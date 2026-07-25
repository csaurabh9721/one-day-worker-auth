package com.onedayworker.auth.service;

import com.onedayworker.auth.dto.DeviceSessionDto;
import com.onedayworker.auth.entity.DeviceSession;
import com.onedayworker.auth.entity.Identity;
import com.onedayworker.auth.mapper.DeviceSessionMapper;
import com.onedayworker.auth.repository.DeviceSessionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceSessionService {

    private final DeviceSessionRepository repository;

    @Transactional(readOnly = true)
    public List<DeviceSessionDto> findAll() {
        return repository.findAll().stream()
                .map(DeviceSessionMapper::toDto)
                .toList();
    }


    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("DeviceSession not found: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }

}
