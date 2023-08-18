package com.be.spring.device.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@Table(name = "data")
@AllArgsConstructor
public class DeviceData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dataId", updatable = false)
    private Long dataId;

    @ManyToOne
    @JoinColumn(name = "deviceId", nullable = false)
    private Device device;

    @Column(name = "macAddress", nullable = false, unique = true)
    private String macAddress;

    @Column(name = "temperatureCelsius", nullable = false)
    private Double temperatureCelsius;

    @Column(name = "humidity", nullable = false)
    private Double humidity;

    @Column(name = "illuminance", nullable = false)
    private Double illuminance;

    @Column(name = "recordedAt", nullable = false)
    private LocalDateTime recordedAt;
}