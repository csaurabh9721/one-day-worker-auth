package com.onedayworker.auth.service;

import com.onedayworker.auth.dto.DeviceSessionDto;
import com.onedayworker.auth.entity.DeviceSession;
import com.onedayworker.auth.mapper.DeviceSessionMapper;
import com.onedayworker.auth.repository.DeviceSessionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("DeviceSession not found: " + id);
        }
        repository.deleteById(id);
    }

    public DeviceSession saveSession(DeviceSession session) {
        return repository.save(session);
    }

    @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }


}
