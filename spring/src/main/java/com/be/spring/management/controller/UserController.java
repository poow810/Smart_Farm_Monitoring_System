package com.be.spring.management.controller;


import com.be.spring.management.dto.AddUserRequest;
import com.be.spring.management.dto.JwtToken;
import com.be.spring.management.dto.MailRequest;
import com.be.spring.management.service.MailService;
import com.be.spring.management.service.RefreshTokenService;
import com.be.spring.management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class UserController {

    private final UserService userService;
    private final MailService mailService;
    private final RefreshTokenService refreshTokenService;

    // 회원 가입 기능
    @PostMapping("/signup")
    public RedirectView signup(@RequestBody AddUserRequest request) {
        userService.save(request);
        return new RedirectView("login");
    }

    // 회원 가입 시 중복 검사
    @GetMapping("/signup/{userId}/exists")
    public ResponseEntity<Boolean> checkUserIdDuplicate(@PathVariable String userId) {
        return ResponseEntity.ok(userService.checkIdDuplicate(userId));
    }

    // 로그인 기능
    @PostMapping("/login")
    public JwtToken login(@RequestBody AddUserRequest request) {
        String userId = request.getUserId();
        String password = request.getPassword();
        return userService.login(userId, password);
    }

    // 아이디 찾기 기능

    @PostMapping("/findId")
    public ResponseEntity<String> findUserId(@RequestBody MailRequest request) {
        String userId = userService.getUserIdByEmail(request.getEmail());

        if(userId != null) {
            mailService.sendUserIdToEmail(request.getEmail(), userId);
            return ResponseEntity.ok("메일이 성공적으로 발송되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("이메일에 해당하는 아이디가 존재하지 않습니다.");
        }
    }
}