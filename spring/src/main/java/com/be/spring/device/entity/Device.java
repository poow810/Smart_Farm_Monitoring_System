package com.be.spring.device.entity;


import com.be.spring.management.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "devices")
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id", updatable = false)
    private Long id;

    @Column(name = "device_number", nullable = false)
    private int deviceNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Device(int deviceNumber, User user) {
        this.deviceNumber = deviceNumber;
        this.user = user;
    }

}
