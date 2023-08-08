package com.be.spring.management.controller;

import com.be.spring.management.dto.MailRequest;
import com.be.spring.management.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @ResponseBody
    @PostMapping("/mail")
    public String MailSend(@RequestBody MailRequest request) {
        int number = mailService.sendMail(request.getEmail());
        String num = ""+number;
        return num;
    }
}
