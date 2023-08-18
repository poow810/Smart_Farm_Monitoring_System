package com.be.spring.device.service;


import com.be.spring.device.dto.DataDto;
import com.be.spring.device.entity.Device;
import com.be.spring.device.entity.DeviceData;
import com.be.spring.device.repository.DataRepository;
import com.be.spring.device.repository.DeviceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataService {

    private final DataRepository dataRepository;
    private final DeviceRepository deviceRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Double> getData(String userId, String farmLabel, String type) {
        // userId와 farmLabel을 사용해 macAddress 찾기
        List<Device> devices = deviceRepository.findAllByUserIdAndFarmLabel(userId, farmLabel);

        if (devices.isEmpty()) {
            throw new RuntimeException("No devices found for the given userId and farmLabel");
        }

        List<DeviceData> dataList = new ArrayList<>();

        for (Device device : devices) {
            dataList.addAll(dataRepository.findAllByMacAddress(device.getMacAddress()));
        }

        // type 별로 데이터를 추출하여 반환
        return dataList.stream()
                .map(data -> extractDataByType(data, type))
                .collect(Collectors.toList());
    }

    private Double extractDataByType(DeviceData data, String type) {
        return switch (type) {
            case "temperature" -> data.getTemperatureCelsius();
            case "humidity" -> data.getHumidity();
            case "illuminance" -> data.getIlluminance();
            default -> throw new IllegalArgumentException("Invalid type: " + type);
        };
    }
}




