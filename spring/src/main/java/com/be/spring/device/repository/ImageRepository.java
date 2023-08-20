package com.be.spring.device.repository;

import com.be.spring.device.entity.DeviceImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<DeviceImage, Long> {

}
