//package com.be.spring.device;
//
//import com.be.spring.device.entity.Device;
//import com.be.spring.device.repository.DeviceRepository;
//import com.be.spring.management.entity.User;
//import com.be.spring.management.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//
//@Service
//@RequiredArgsConstructor
//public class DeviceService {
//
//    private final DeviceRepository deviceRepository;
//    private final UserRepository userRepository;
//
//    public Device addDeviceToUser(int deviceNumber, Long userId) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with id" + userId));
//
//        Device device = new Device(deviceNumber, user);
//        device.setUser(user);
//
//        return deviceRepository.save(device);
//    }
//}
