package com.be.spring.management.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class JwtUtilityService {

    private final TokenProvider tokenProvider;


    public String getUserIdFromToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7); // "Bearer " 제거
        Authentication authentication = tokenProvider.getAuthentication(token);
        return authentication.getName();
    }
}