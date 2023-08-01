package com.be.spring.management.service;

import com.be.spring.management.config.jwt.TokenProvider;
import com.be.spring.management.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;



    public String createNewAccessToken(String refreshToken) {
        if(!tokenProvider.validToken(refreshToken)) { // 토큰 유효성 검사 실패 시 예외 발생
            throw new IllegalArgumentException("Unexpected token");
        }
        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findById(userId);
        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }
}
