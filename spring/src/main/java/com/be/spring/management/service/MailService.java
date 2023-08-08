package com.be.spring.management.service;


import com.be.spring.management.entity.User;
import com.be.spring.management.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private static final String senderEmail = "poow810@gmail.com";
    private static int number;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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

    // 아이디를 찾기 위한 메일 발송
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

    // 비밀번호 재설정을 위한 메일 발송
    public String sendPasswordToEmail(String email) {
        String tempPassword = generateTempPassword();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject("비밀번호 재설정");
            String body ="";
            body += "<h3>" + "회원님의 임시 비밀번호입니다." + "</h3>";
            body += "<h1>" + tempPassword + "</h1>";
            body += "<h3>" + "로그인 후 반드시 비밀번호를 변경해주세요." + "</h3>";
            message.setText(body, "UTF-8", "html");
        }catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(message);
        return tempPassword;
    }

    // 임시 비밀번호 생성
    public String generateTempPassword() {
        StringBuilder sb = new StringBuilder();
        for (int i =0; i < 8; i++) {
            int index = (int) (Math.random() * "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".length());
            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".charAt(index));
        }
        return sb.toString();
    }
}