package com.be.spring.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


// 로그인과 회원 가입 시 view controller
@Controller
public class UserViewController {

    @GetMapping("/login")
    public String login() {
        return "oauthLogin";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/articles")
    public String getArticle() {
        return "logout";
    }
}

