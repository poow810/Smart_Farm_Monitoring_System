package com.be.spring.device.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@Table(name = "image")
@NoArgsConstructor
public class DeviceImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp", nullable = false)
    private String timestamp;

    @Column(name = "macAddress", nullable = false)
    private String macAddress;


    @Column(name = "imageURL")
    private String imageURL;
}
