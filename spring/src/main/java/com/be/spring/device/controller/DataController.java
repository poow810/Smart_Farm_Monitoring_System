package com.be.spring.device.controller;


import com.be.spring.device.dto.DataDto;
import com.be.spring.device.entity.DeviceData;
import com.be.spring.device.service.DataService;
import com.be.spring.device.service.DeviceService;
import com.be.spring.management.config.jwt.JwtUtilityService;
import com.be.spring.management.config.jwt.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/device")
@RequiredArgsConstructor
public class DataController {

    private final DataService dataService;
    private final TokenProvider tokenProvider;
    private final DeviceService deviceService;
    private final JwtUtilityService jwtUtilityService;

    @GetMapping("/{type}")
    public ResponseEntity<List<Double>> getSpecificDataByType(
            @RequestParam String farmLabel,
            @PathVariable String type,
            HttpServletRequest request) {

        // JWT 토큰에서 userId 추출
        String userId = jwtUtilityService.getUserIdFromToken(request);

        List<Double> dataList = dataService.getData(userId, farmLabel, type);
        return ResponseEntity.ok(dataList);
    }

    @GetMapping("/lastest")
    public ResponseEntity<List<DeviceData>> getLatestDataForAllDevices(HttpServletRequest request) {
        String userId = jwtUtilityService.getUserIdFromToken(request);
        List<DeviceData> latestDataList = dataService.getLastestData(userId);
        return ResponseEntity.ok(latestDataList);
    }

}
