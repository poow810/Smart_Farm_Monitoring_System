package com.be.spring.management.controller;


import com.be.spring.management.dto.PasswordChangeRequest;
import com.be.spring.management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/changePw")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeRequest request, Principal principal) {
        String userId = principal.getName();
        boolean isChanged = userService.changePassword(userId, request.getOldPassword(), request.getNewPassword());

        if (isChanged) {
            return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("현재 비밀번호가 올바르지 않습니다.");
        }
    }
}
