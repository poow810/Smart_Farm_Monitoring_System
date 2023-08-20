package com.be.spring.device.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceRequest {
    private List<String> farmLabels;
    private List<String> macAddresses;
}
