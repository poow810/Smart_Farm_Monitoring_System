package com.be.spring.device.service;


import com.be.spring.device.dto.DataDto;
import com.be.spring.device.entity.Device;
import com.be.spring.device.entity.DeviceData;
import com.be.spring.device.repository.DataRepository;
import com.be.spring.device.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataService {

    private static final String ARDUINO_URL = "http://";
    private final RestTemplate restTemplate;
    private final DataRepository dataRepository;
    private final DeviceRepository deviceRepository;

    public void saveData(DataDto dataDto) {
        Device device = deviceRepository.findById(dataDto.getDeviceId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Device ID: " + dataDto.getDeviceId()));

        dataRepository.save(DeviceData.builder()
                .device(device)
                .humidity(dataDto.getHumidity())
                .illuminance(dataDto.getIlluminance())
                .temperature(dataDto.getTemperature())
                .recordedAt(dataDto.getRecordedAt())
                .build());
    }

    public List<DataDto> getAllData() {
        return dataRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private DataDto convertToDto(DeviceData deviceData) {
        DataDto dto = new DataDto();
        dto.setHumidity(deviceData.getHumidity());
        dto.setIlluminance(deviceData.getIlluminance());
        dto.setTemperature(deviceData.getTemperature());
        dto.setRecordedAt(deviceData.getRecordedAt());
        return dto;
    }

    // Arduino의 데이터를 받아오기 위한 메서드
    @Scheduled(fixedRate = 5000)
    public DataDto fetchTemperatureFromArduino() {
        DataDto parsingData = null;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(ARDUINO_URL, String.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                String responseBody = response.getBody();
                parsingData = parseTemperatureResponse(responseBody);
            } else {
                System.out.println("Failed to fetch data from Arduino server.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (parsingData != null) {
            parsingData.setRecordedAt(LocalDateTime.now());
            saveData(parsingData);
        }
        return parsingData;
    }

    // json 형식으로 parsing
    private DataDto parseTemperatureResponse(String responseBody) {
        // 데이터 파싱 부분
        JSONObject jsonObject = new JSONObject(responseBody);
        double humidity = jsonObject.getDouble("humidity");
        double illuminance = jsonObject.getDouble("illuminance");
        double temperature = jsonObject.getDouble("temperature");
        Long deviceId = jsonObject.getLong("deviceId");

        DataDto parsingData = new DataDto();
        parsingData.setHumidity(humidity);
        parsingData.setIlluminance(illuminance);
        parsingData.setTemperature(temperature);
        parsingData.setDeviceId(deviceId);  // 추가: 디바이스 ID 설정

        return parsingData;
    }

}
