package com.be.spring.device.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DataDto {

    // device 데이터를 전달
    private Long deviceId;
    private String macAddress;
    private double humidity;
    private double illuminance;
    private double temperatureCelsius;
    private String currentTime;


    // image 전달
    private String image;
    private String time;
}
