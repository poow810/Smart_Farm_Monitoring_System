package com.be.spring.management.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private static final String senderEmail = "poow810@gmail.com";
    private static int number;

    public static void createNumber() {
        number = (int)(Math.random() * (90000)) + 100000;
    }

    public MimeMessage CreateMail(String mail) {
        createNumber();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("이메일 인증");
            String body = "";
            body += "<h3>" + "요청하신 인증 번호입니다." +"</h3>";
            body += "<h1>" + number +"</h1>";
            body += "<h3>" + "감사합니다." +"</h3>";
            message.setText(body, "UTF-8", "html");
        }catch (MessagingException e) {
            e.printStackTrace();
        }
        return message;
    }

    // 이메일 입력 시 메일로 해당 유저의 아이디 발송
    public void sendUserIdToEmail(String email, String userId) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject("아이디  찾기");
            String body = "";
            body += "<h3>" + "회원님의 아이디 입니다." +"</h3>";
            body += "<h1>" + userId +"</h1>";
            body += "<h3>" + "감사합니다." +"</h3>";
            message.setText(body, "UTF-8", "html");
        }catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(message);
    }

    public int sendMail(String mail){
        MimeMessage message = CreateMail(mail);
        javaMailSender.send(message);

        return number;
    }
}