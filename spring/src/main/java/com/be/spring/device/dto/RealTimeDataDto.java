package com.be.spring.device.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class RealTimeDataDto {

    private LocalDateTime recordedAt;
    private Map<Long, Double> temperatureData;
}
