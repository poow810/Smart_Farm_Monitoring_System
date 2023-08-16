package com.be.spring.management.controller;


import com.be.spring.management.config.jwt.TokenProvider;
import com.be.spring.management.dto.AddUserRequest;
import com.be.spring.management.dto.CreateAccessTokenRequest;
import com.be.spring.management.dto.CreateAccessTokenResponse;
import com.be.spring.management.dto.JwtToken;
import com.be.spring.management.entity.User;
import com.be.spring.management.service.TokenService;
import com.be.spring.management.service.UserDetailService;
import com.be.spring.management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("/token")
public class TokenApiController {

    private final TokenProvider tokenProvider;
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> payload) {
        String refreshToken = payload.get("refreshToken");
        if (refreshToken == null || !tokenProvider.validateRefreshToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token.");
        }

        Authentication authentication = tokenProvider.getAuthenticationRefreshToken(refreshToken);
        JwtToken newTokens = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(newTokens);
    }
}