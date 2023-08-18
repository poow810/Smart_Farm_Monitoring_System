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
    private final RefreshTokenService refreshTokenService;


    // 회원가입 서비스
    public Long save(AddUserRequest dto) {
        return userRepository.save(User.builder()
                .userId(dto.getUserId())
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .build()).getId();
    }

    // 아이디 중복 검사
    public boolean checkIdDuplicate(String userId) {
        return userRepository.existsByUserId(userId);
    }


    // 로그인 서비스
    public JwtToken login(String userId, String password) {
        // Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 검증된 인증 정보로 JWT 토큰 생성
        JwtToken token = tokenProvider.generateToken(authentication);

        // 아이디 기반 사용자 ID 검색
        String userIdInDB = getIdByUserId(userId);

        // RefreshToken 객체 생성
        RefreshToken refreshToken = new RefreshToken(userIdInDB, token.getRefreshToken());

        // 데이터 베이스에 refreshToken 저장
        refreshTokenRepository.save(refreshToken);

        return token;
    }

    // 유저의 이메일을 통해 userId 추적
    public String getUserIdByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(User::getUserId).orElse(null);
    }

    // 유저의 이메일과 해당 email로 가입된 userId가 있는지, 일치하는지 조회
    public boolean userEmailCheck(String email, String userId) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.map(user -> user.getUserId().equals(userId)).orElse(false);
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
    // 비밀번호 변경
    public boolean changePassword(String userId, String oldPassword, String newPassword) {
        User user = userRepository.findByUserId(userId).orElse(null);

        if (user == null) {
            return false;
        }
        if (bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
            updatePassword(newPassword, userId);
            return true;
        }
        return false;
    }
    // 비밀번호 업데이트
    public void updatePassword(String newPassword, String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 유저의 아이디를 찾을 수 없습니다."));

        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        userRepository.save(user);
    }


    // 유저의 아이디를 통해 사용자의 id 추적
    public String getIdByUserId(String userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        return user.map(User::getUserId).orElse(null);
    }
}