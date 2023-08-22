package com.be.spring.mqtt;

import com.be.spring.device.dto.DataDto;
import com.be.spring.device.entity.DeviceData;
import com.be.spring.device.entity.DeviceImage;
import com.be.spring.device.repository.DataRepository;
import com.be.spring.device.repository.ImageRepository;
import com.be.spring.device.service.ImageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MqttService {

    private final DataRepository dataRepository;
    private final ImageRepository imageRepository;
    private final ImageService imageService;

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleMessage(Message<?> message) {
        System.out.println("Received MQTT message: " + message.getPayload());
        String topic = (String) message.getHeaders().get("mqtt_receivedTopic");

        if ("smartfarm/sensor".equals(topic)) {
            DataDto dataDto = convertToDto((String) message.getPayload());
            DeviceData deviceData = convertSensorDataToEntity(dataDto);
            dataRepository.save(deviceData);
        } else if ("smartfarm/images".equals(topic)) {
            DataDto imageDto = convertImageToDto((String) message.getPayload());

            // 이미지를 GCP에 업로드
            String imageName = imageDto.getMacAddress() + "-" + imageDto.getTime() + ".jpg";
            String imageUrl = imageService.uploadImage(imageDto.getImage(), imageName);
            System.out.println("Image uploaded to GCP Storage: " + imageUrl);

            // 이미지 URL을 이용해 Entity를 생성
            DeviceImage deviceImage = convertImageDataToEntity(imageDto, imageUrl);

            // DB에 저장
            imageRepository.save(deviceImage);
        }
    }

    private DataDto convertToDto(String payload) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(payload, DataDto.class);
        } catch (IOException e) {
            throw new RuntimeException("변환에 실패했습니다.", e);
        }
    }

    private DeviceData convertSensorDataToEntity(DataDto dto) {
        LocalTime time = LocalTime.parse(dto.getCurrentTime(), DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalDateTime recordedAt = LocalDateTime.of(LocalDate.now(), time);

        return DeviceData.builder()
                .macAddress(dto.getMacAddress())
                .temperatureCelsius(dto.getTemperatureCelsius())
                .humidity(dto.getHumidity())
                .illuminance(dto.getIlluminance())
                .recordedAt(recordedAt).build();
    }

    private DataDto convertImageToDto(String payload) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(payload, DataDto.class);
        } catch (IOException e) {
            throw new RuntimeException("Image 변환에 실패했습니다.", e);
        }
    }

    private DeviceImage convertImageDataToEntity(DataDto imageDto, String imageURL) {
        // 이미지 URL을 사용해서 DeviceImage 엔터티 객체를 생성하고 반환하는 로직
        // 예를 들면:
        DeviceImage deviceImage = new DeviceImage();
        deviceImage.setMacAddress(imageDto.getMacAddress());
        deviceImage.setImageURL(imageURL);
        deviceImage.setTimestamp(imageDto.getTime());
        return deviceImage;
    }

    private String generateUniqueImageName(String time) {
        // time을 기반으로 한 유니크한 이미지 파일 이름을 생성
        return time + "_" + UUID.randomUUID().toString() + ".jpg";
    }
}
