package com.be.spring.device.repository;

import com.be.spring.device.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findAllByUserIdAndFarmLabel(String userId, String farmLabel);

    Device findByMacAddress(String macAddress);

}
