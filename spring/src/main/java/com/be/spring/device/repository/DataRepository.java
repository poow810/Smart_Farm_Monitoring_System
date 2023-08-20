package com.be.spring.device.repository;

import com.be.spring.device.entity.DeviceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataRepository extends JpaRepository<DeviceData, Long> {
    List<DeviceData> findAllByMacAddress(String macAddresses);

    @Query("SELECT d FROM DeviceData d WHERE d.macAddress = :macAddress AND d.recordedAt = (SELECT MAX(d2.recordedAt) FROM DeviceData d2 WHERE d2.macAddress = :macAddress)")
    DeviceData findLatestDataForMacAddress(String macAddress);
}