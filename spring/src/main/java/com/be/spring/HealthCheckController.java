package com.be.spring;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;

@RestController
@RequiredArgsConstructor
public class HealthCheckController {

    private final DataSource dataSource;

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(2)) { // 2초 이내에 연결이 유효한지 확인
                return ResponseEntity.ok("Health Check: OK");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Health Check: Database connection is not valid");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Health Check: Error connecting to database");
        }
    }

}
