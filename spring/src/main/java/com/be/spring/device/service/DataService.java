//package com.be.spring.device.service;
//
//
//import com.be.spring.device.dto.DataDto;
//import com.be.spring.device.dto.RealTimeDataDto;
//import com.be.spring.device.entity.Device;
//import com.be.spring.device.entity.DeviceData;
//import com.be.spring.device.repository.DataRepository;
//import com.be.spring.device.repository.DeviceRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class DataService {
//
//    private static final String ARDUINO_URL = "http://";
//    private final RestTemplate restTemplate;
//    private final DataRepository dataRepository;
//    private final DeviceRepository deviceRepository;
//
//    public void saveData(DataDto dataDto) {
//        Device device = deviceRepository.findById(dataDto.getDeviceId())
//                .orElseThrow(() -> new IllegalArgumentException("Invalid Device ID: " + dataDto.getDeviceId()));
//
//        dataRepository.save(DeviceData.builder()
//                .device(device)
//                .humidity(dataDto.getHumidity())
//                .illuminance(dataDto.getIlluminance())
//                .temperature(dataDto.getTemperature())
//                .recordedAt(dataDto.getRecordedAt())
//                .build());
//    }
//
//    // 모든 데이터 가져오기
//    public List<DataDto> getAllByType(String type) {
//        return dataRepository.findAll().stream()
//                .map(data -> filterByType(data, type))
//                .collect(Collectors.toList());
//    }
//
//
//    // deviceId 별 데이터 가져오기
//    public RealTimeDataDto getCurrentTemperature() {
//        List<DeviceData> dataList = dataRepository.findAllByRecorded(LocalDateTime.now());
//
//        RealTimeDataDto RealTimeDataDto = new RealTimeDataDto();
//        RealTimeDataDto.setRecordedAt(LocalDateTime.now());
//
//        Map<Long, Double> temperatureMap = new HashMap<>();
//        for (DeviceData data : dataList) {
//            temperatureMap.put(data.getDevice().getDeviceId(), data.getTemperature());
//        }
//
//        RealTimeDataDto.setTemperatureData(temperatureMap);
//
//        return RealTimeDataDto;
//    }
//
//    private DataDto filterByType(DeviceData deviceData, String type) {
//        DataDto dto = new DataDto();
//        dto.setDeviceId(deviceData.getDevice().getDeviceId());
//        dto.setRecordedAt(deviceData.getRecordedAt());
//
//        switch (type) {
//            case "temperature" -> dto.setTemperature(deviceData.getTemperature());
//            case "illuminance" -> dto.setIlluminance(deviceData.getIlluminance());
//            case "humidity" -> dto.setHumidity(deviceData.getHumidity());
//            default -> throw new IllegalArgumentException("Invalid type: " + type);
//        }
//        return dto;
//    }
//
//    private DataDto convertToDto(DeviceData deviceData) {
//        DataDto dto = new DataDto();
//        dto.setHumidity(deviceData.getHumidity());
//        dto.setIlluminance(deviceData.getIlluminance());
//        dto.setTemperature(deviceData.getTemperature());
//        dto.setRecordedAt(deviceData.getRecordedAt());
//        return dto;
//    }
//
//    @Scheduled(fixedRate = 5000)
//    public void fetchTemperatureFromArduino() {
//        try {
//            ResponseEntity<DataDto> response = restTemplate.getForEntity(ARDUINO_URL, DataDto.class);
//            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
//                DataDto receivedData = response.getBody();
//                receivedData.setRecordedAt(LocalDateTime.now());
//                saveData(receivedData);
//            } else {
//                System.out.println("Failed to fetch data from Arduino server.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//}
