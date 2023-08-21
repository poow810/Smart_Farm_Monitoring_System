package com.be.spring.device.controller;


import com.be.spring.device.dto.DeviceRequest;
import com.be.spring.device.entity.Device;
import com.be.spring.device.service.DeviceService;
import com.be.spring.management.config.jwt.JwtUtilityService;
import com.be.spring.management.config.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;
    private final JwtUtilityService jwtUtilityService;

    @GetMapping("/data")
    public ResponseEntity<List<DeviceRequest>> showDevices(HttpServletRequest request) {
        String userId = jwtUtilityService.getUserIdFromToken(request);
        List<DeviceRequest> deviceRequests = deviceService.showDeviceToUser(userId);
        return ResponseEntity.ok(deviceRequests);
    }

    @PostMapping("/device")
    public List<Device> addDevices(@RequestBody DeviceRequest deviceRequest, HttpServletRequest request) {
        String userId = jwtUtilityService.getUserIdFromToken(request);
        return deviceService.addDevice(userId, deviceRequest.getFarmLabels(), deviceRequest.getMacAddresses());
    }

    @PutMapping("/farmLabel")
    public ResponseEntity<?> updateFarmLabels(@RequestBody DeviceRequest request) {
        deviceService.updateFarmLabel(request.getMacAddresses(), request.getFarmLabels());
        return ResponseEntity.ok().body("Farm labels updated successfully.");
    }

}
