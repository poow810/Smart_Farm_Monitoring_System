package com.be.spring.device.repository;

import com.be.spring.device.entity.DeviceImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<DeviceImage, Long> {

}
