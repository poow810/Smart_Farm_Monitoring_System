package com.be.spring.device.entity;

import com.be.spring.management.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "device")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@AllArgsConstructor
@Builder
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deviceId", updatable = false)
    private Long deviceId;

    @Column(name = "farmLabel")
    private String farmLabel;

    @Column(name = "userId", nullable = false)
    private String userId;

    @Column(name = "macAddress", nullable = false)
    private String macAddress;
    // 추가 필드 - 디바이스 타입, 모델 번호
}