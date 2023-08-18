package com.be.spring.device;

import com.be.spring.device.dto.DataDto;
import com.be.spring.device.entity.Device;
import com.be.spring.device.entity.DeviceData;
import com.be.spring.device.repository.DataRepository;
import com.be.spring.device.repository.DeviceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class MqttService {

    private final DataRepository dataRepository;
    private final DeviceRepository deviceRepository;

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleMessage(Message<?> message) {
        System.out.println("Received MQTT message: " + message.getPayload());
        // MQTT -> DTO
        DataDto dataDto = convertToDto((String) message.getPayload());
        // DTO -> ENTITY
        DeviceData deviceData = convertToEntity(dataDto);
        dataRepository.save(deviceData);
    }

    private DataDto convertToDto(String payload) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(payload, DataDto.class);
        }catch (IOException e) {
            throw new RuntimeException("변환에 실패했습니다.", e);
        }
    }

    private DeviceData convertToEntity(DataDto dto) {
        Device device = deviceRepository.findByMacAddress(dto.getMacAddress());
        if(device == null) {
            throw new RuntimeException("Mac 주소가 일치하지 않습니다.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime recordedAt = LocalDateTime.parse(dto.getCurrentTime(), formatter);

        return DeviceData.builder()
                .device(device)
                .macAddress(dto.getMacAddress())
                .temperatureCelsius(dto.getTemperatureCelsius())
                .humidity(dto.getHumidity())
                .illuminance(dto.getIlluminance())
                .recordedAt(recordedAt).build();

    }
}
