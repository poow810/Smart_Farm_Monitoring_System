package com.be.spring.device.repository;

import com.be.spring.device.entity.DeviceData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataRepository extends JpaRepository<DeviceData, Long> {
}
