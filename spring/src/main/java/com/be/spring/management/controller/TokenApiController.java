package com.be.spring.management.controller;


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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
public class TokenApiController {

    private final UserService userService;

//    @PostMapping("/api/token")
//    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(@RequestBody CreateAccessTokenRequest request) {
//        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());
//
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(new CreateAccessTokenResponse(newAccessToken));
//    }

//    @PostMapping("/login")
//    public ResponseEntity<JwtToken> authenticateUser(@RequestBody Map<String, String>) {
//        JwtToken token = userService.login(loginForm.get("username"), loginForm.get("password"));
//        return ResponseEntity.ok(token);
//    }
}