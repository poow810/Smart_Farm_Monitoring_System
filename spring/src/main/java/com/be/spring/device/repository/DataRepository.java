package com.be.spring.device.repository;

import com.be.spring.device.entity.Device;
import com.be.spring.device.entity.DeviceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DataRepository extends JpaRepository<DeviceData, Long> {
    // deviceId 목록을 기반으로 데이터 검색하고 type에 따라 필터링 (온도, 습도, 조도)
    @Query("SELECT d FROM DeviceData d WHERE d.serialNumber IN :serialNumbers AND (CASE WHEN :type = 'temperature' THEN d.temperature IS NOT NULL WHEN :type = 'illuminance' THEN d.illuminance IS NOT NULL WHEN :type = 'humidity' THEN d.humidity IS NOT NULL END)")
    List<DeviceData> findBySerialNumberInAndType(@Param("serialNumbers") List<String> serialNumbers, @Param("type") String type);
}