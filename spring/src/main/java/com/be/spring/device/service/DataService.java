package com.be.spring.device.service;


import com.be.spring.device.dto.DataDto;
import com.be.spring.device.entity.Device;
import com.be.spring.device.entity.DeviceData;
import com.be.spring.device.repository.DataRepository;
import com.be.spring.device.repository.DeviceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataService implements MqttCallback {

    @Value("${mqtt.url}")
    private String mqttServer;

    @Value("${mqtt.username}")
    private String mqttUsername;

    @Value("${mqtt.password}")
    private String mqttPassword;

    @Value("${mqtt.topic}")
    private String topic;

    private final DataRepository dataRepository;
    private final DeviceRepository deviceRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void init() throws MqttException {
        MqttClient mqttClient = new MqttClient(mqttServer, MqttClient.generateClientId(), new MemoryPersistence());
        mqttClient.setCallback(this);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setUserName(mqttUsername);
        options.setPassword(mqttPassword.toCharArray());
        mqttClient.connect(options);
        mqttClient.subscribe(topic);
    }

    @Override
    public void connectionLost(Throwable cause) {
        // 연결이 끊어졌을 때 로직
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String payload = new String(message.getPayload());
        DataDto receivedData = objectMapper.readValue(payload, DataDto.class);

        // MQTT 토픽에서 sensorId 추출
        String serialNumber = topic.split("/")[1]; // "smartfarm/Sensor1" -> "Sensor1"
        receivedData.setSerialNumber(serialNumber);
        saveData(receivedData);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // 메시지가 성공적으로 전달되었을 때 로직
    }

    public void saveData(DataDto dataDto) {
        Device device = deviceRepository.findById(dataDto.getDeviceId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Device ID: " + dataDto.getDeviceId()));

        dataRepository.save(DeviceData.builder()
                .device(device)
                .serialNumber(dataDto.getSerialNumber())
                .humidity(dataDto.getHumidity())
                .illuminance(dataDto.getIlluminance())
                .temperature(dataDto.getTemperature())
                .recordedAt(dataDto.getRecordedAt())
                .build());
    }

    public List<DataDto> getFilterData(String userId, String farmLabel, String type) {
        List<Device> userDevices = deviceRepository.findAllByUserIdAndFarmLabel(userId, farmLabel);
        if (userDevices.isEmpty()) {
            throw new IllegalArgumentException("not present serialNumber");
        }
        List<String> SerialNumbers = userDevices.stream().map(Device::getSerialNumber).collect(Collectors.toList());

        List<DeviceData> dataList = dataRepository.findBySerialNumberInAndType(SerialNumbers, type);

        return dataList.stream()
                .map(data -> convertToDto(data, type))
                .collect(Collectors.toList());
    }

    private DataDto convertToDto(DeviceData deviceData, String type) {
        DataDto dto = new DataDto();
        dto.setDeviceId(deviceData.getDevice().getDeviceId());
        dto.setRecordedAt(deviceData.getRecordedAt());

        switch (type) {
            case "temperature" -> dto.setTemperature(deviceData.getTemperature());
            case "illuminance" -> dto.setIlluminance(deviceData.getIlluminance());
            case "humidity" -> dto.setHumidity(deviceData.getHumidity());
            default -> throw new IllegalArgumentException("Invalid type: " + type);
        }
        return dto;
    }
}