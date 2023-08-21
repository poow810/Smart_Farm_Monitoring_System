package com.be.spring.device.service;

import com.be.spring.device.dto.DeviceRequest;
import com.be.spring.device.repository.DeviceRepository;
import com.be.spring.device.entity.Device;
import com.be.spring.management.entity.User;
import com.be.spring.management.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;

    // 디바이스 추가
    public List<Device> addDevice(String userId, List<String> farmLabels, List<String> macAddresses) {
        List<Device> devices = new ArrayList<>();

        for (int i = 0; i < farmLabels.size(); i++) {
            Device device = Device.builder()
                    .userId(userId)
                    .farmLabel(farmLabels.get(i))
                    .macAddress(macAddresses.get(i))
                    .build();
            devices.add(device);
        }

        return deviceRepository.saveAll(devices);
    }

    // 디바이스 가져오기
    public List<DeviceRequest> showDeviceToUser(String userId) {
        List<Device> devices = deviceRepository.findByUserId(userId);

        if(devices.isEmpty()) {
            throw new EntityNotFoundException("Device not found for userId: " + userId);
        }

        return devices.stream()
                .map(device -> new DeviceRequest(
                        Collections.singletonList(device.getFarmLabel()),
                        Collections.singletonList(device.getMacAddress())
                ))
                .collect(Collectors.toList());
    }

    // 디바이스 정보 바꾸기
    public void updateFarmLabel(List<String> macAddresses, List<String> farmLabels) {
        if(macAddresses.size() != farmLabels.size()) {
            throw new RuntimeException("Size of macAddresses and types must be the same.");
        }

        for(int j = 0; j < macAddresses.size(); j++) {
            int finalJ = j;
            Device device = deviceRepository.findByMacAddress(macAddresses.get(j))
                    .orElseThrow(() -> new RuntimeException("Device not found with macAddress: " + macAddresses.get(finalJ)));

            device.setFarmLabel(farmLabels.get(j));
            deviceRepository.save(device);
        }
    }
}

