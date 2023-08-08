//package com.be.spring.management.entity;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Getter
//@Table(name = "device")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class Device {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private int deviceNumber;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @Builder
//    public Device(int deviceNumber, User user) {
//        this.deviceNumber = deviceNumber;
//        this.user = user;
//    }
//}
