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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MailService mailService;


    // 회원가입 서비스
    public Long save(AddUserRequest dto) {
        return userRepository.save(User.builder()
                .userId(dto.getUserId())
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build()).getId();
    }

    // 아이디 중복 검사
    public boolean checkIdDuplicate(String user_id) {
        return userRepository.existsByUserId(user_id);
    }


    // 로그인 서비스
    public JwtToken login(String email, String password) {
        // Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 검증된 인증 정보로 JWT 토큰 생성
        JwtToken token = tokenProvider.generateToken(authentication);

        // 이메일 기반 사용자 ID 검색
        Long userId = getIdByEmail(email);

        // RefreshToken 객체 생성
        RefreshToken refreshToken = new RefreshToken(userId, token.getRefreshToken());

        // 데이터 베이스에 refreshToken 저장
        refreshTokenRepository.save(refreshToken);

        return token;
    }

    // 유저의 이메일을 통해 userId 추적
    public String getUserIdByEmail(String email) {
        System.out.println("Email: " + email); // 이 부분 추가
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(User::getUserId).orElse(null);
    }

    // 아이디 찾기
    public void findUserId(String email) {
        String userId = getUserIdByEmail(email);

        if(userId == null) {
            throw new UsernameNotFoundException("User not found with email" + email);
        }

        String content = "아이디는 : " + userId + "입니다.";
        mailService.sendMail(content);
    }



    // 유저의 이메일을 통해 사용자의 id 추적
    public Long getIdByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(User::getId).orElse(null);
    }
}