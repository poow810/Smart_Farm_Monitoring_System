package com.be.spring.device.repository;

import com.be.spring.device.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findAllByUserIdAndFarmLabel(String userId, String farmLabel);

    Optional<Device> findByMacAddress(String macAddress);    // macAddress를 통해 farmLabel 변경

    List<Device> findAllByUserId(String userId);
    List<Device> findByUserId(String userId);



}
