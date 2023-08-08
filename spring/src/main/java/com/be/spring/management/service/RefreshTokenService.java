package com.be.spring.management.service;

import com.be.spring.management.entity.RefreshToken;
import com.be.spring.management.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public void saveRefreshToken(Long userId, String newRefreshToken) {
        // 해당 사용자의 기존 리프레시 토큰을 찾아서 삭제
        refreshTokenRepository.findByUserId(userId).ifPresent(refreshTokenRepository::delete);

        // 새로운 리프레시 토큰 저장
        RefreshToken refreshToken = new RefreshToken(userId, newRefreshToken);
        refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
    }
}
