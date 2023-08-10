package com.be.spring.device.controller;


import com.be.spring.device.dto.DataDto;
import com.be.spring.device.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/device")
@RequiredArgsConstructor
public class DataController {

    private final DataService dataService;


    @GetMapping("/data")
    public ResponseEntity<List<DataDto>> getAllData() {
        List<DataDto> dataList = dataService.getAllData();
        return ResponseEntity.ok(dataList);
    }
}
