package com.be.spring.management.controller;


import com.be.spring.management.dto.AddUserRequest;
import com.be.spring.management.dto.JwtToken;
import com.be.spring.management.service.RefreshTokenService;
import com.be.spring.management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class UserController {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    // 회원 가입 기능
    @PostMapping("/signup")
    public String signup(@RequestBody AddUserRequest request) {
        userService.save(request);
        return "redirect:/login";
    }

    // 회원 가입 시 중복 검사
    @GetMapping("/signup/{userId}/exists")
    public ResponseEntity<Boolean> checkUserIdDuplicate(@PathVariable String userId) {
        return ResponseEntity.ok(userService.checkIdDuplicate(userId));
    }

    // 로그인 기능
    @PostMapping("/login")
    public JwtToken login(@RequestBody AddUserRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();
        return userService.login(email, password);
    }
}
