package com.be.spring.device.repository;

import com.be.spring.device.entity.DeviceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataRepository extends JpaRepository<DeviceData, Long> {
    List<DeviceData> findAllByMacAddress(String macAddresses);
}