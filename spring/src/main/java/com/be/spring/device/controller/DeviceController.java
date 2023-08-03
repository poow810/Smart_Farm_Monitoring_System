//package com.be.spring.device.controller;
//
//
//import com.be.spring.device.DeviceService;
//import com.be.spring.device.entity.Device;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/devices")
//public class DeviceController {
//
//    private final DeviceService deviceService;
//
//    public DeviceController(DeviceService deviceService) {
//        this.deviceService = deviceService;
//    }
//
//    @PostMapping("/{userId}")
//    public Device addDevice(@RequestBody int deviceNumber, @PathVariable Long userId) {
//        return deviceService.addDeviceToUser(deviceNumber, userId);
//    }
//}
