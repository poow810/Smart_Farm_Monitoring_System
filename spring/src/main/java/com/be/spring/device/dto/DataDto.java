package com.be.spring.device.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DataDto {

    // 전달 받는 데이터
    private Long deviceId;
    private String macAddress;
    private double humidity;
    private double illuminance;
    private double temperatureCelsius;
    private String currentTime;
}
