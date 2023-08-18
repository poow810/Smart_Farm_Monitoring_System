package com.be.spring.device.controller;


import com.be.spring.device.dto.DeviceRequest;
import com.be.spring.device.service.DeviceService;
import com.be.spring.device.entity.Device;
import com.be.spring.management.config.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;
    private final TokenProvider tokenProvider;


    @PostMapping("/device")
    public Device addDevice(@RequestBody DeviceRequest deviceRequest, HttpServletRequest request) {
        // 토큰에서 userId 추출
        String token = request.getHeader("Authorization").substring(7); // "Bearer " 제거
        Authentication authentication = tokenProvider.getAuthentication(token);
        String userId = authentication.getName();

        return deviceService.addDeviceToUser(userId, deviceRequest.getFarmLabel(), deviceRequest.getMacAddress());
    }


}
