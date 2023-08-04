package com.be.spring.management.service;


import com.be.spring.management.config.jwt.TokenProvider;
import com.be.spring.management.dto.AddUserRequest;
import com.be.spring.management.dto.JwtToken;
import com.be.spring.management.entity.RefreshToken;
import com.be.spring.management.entity.User;
import com.be.spring.management.repository.RefreshTokenRepository;
import com.be.spring.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;


    // 회원가입 서비스
    public Long save(AddUserRequest dto) {
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build()).getId();
    }


    // 로그인 서비스

    public JwtToken login(String email, String password) {
        // Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 검증된 인증 정보로 JWT 토큰 생성
        JwtToken token = tokenProvider.generateToken(authentication);

        // 이메일 기반 사용자 ID 검색
        Long userId = getUserIdByEmail(email);

        // RefreshToken 객체 생성
        RefreshToken refreshToken = new RefreshToken(userId, token.getRefreshToken());

        // 데이터 베이스에 refreshToken 저장
        refreshTokenRepository.save(refreshToken);

        return token;
    }

    // 유저의 이메일을 통해 사용자의 id 추적
    public Long getUserIdByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(User::getId).orElse(null);
    }
}