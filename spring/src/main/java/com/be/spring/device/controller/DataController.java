//package com.be.spring.device.controller;
//
//
//import com.be.spring.device.dto.RealTimeDataDto;
//import com.be.spring.device.service.DataService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//
//@RestController
//@RequestMapping("/device")
//@RequiredArgsConstructor
//public class DataController {
//
//    private final DataService dataService;
//
//
//    @GetMapping("/currentTemperature")
//    public ResponseEntity<RealTimeDataDto> getCurrentTemperatureForAllDevices() {
//        RealTimeDataDto realTimeDataDto = dataService.getCurrentTemperature();
//        return ResponseEntity.ok(realTimeDataDto);
//    }
//}
