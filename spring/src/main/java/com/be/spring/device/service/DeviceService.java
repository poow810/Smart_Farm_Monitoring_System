package com.be.spring.device.service;

import com.be.spring.device.repository.DeviceRepository;
import com.be.spring.device.entity.Device;
import com.be.spring.management.entity.User;
import com.be.spring.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;


    // 디바이스 추가
    public Device addDeviceToUser(String userId, String farmLabel) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id" + userId));

        Device device = Device.builder()
                .userId(userId)
                .farmLabel(farmLabel)
                .build();

        return deviceRepository.save(device);
    }
}

