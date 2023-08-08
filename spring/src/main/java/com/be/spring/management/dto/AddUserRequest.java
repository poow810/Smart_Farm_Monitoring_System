package com.be.spring.management.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddUserRequest {

    @NotBlank
    @Size(min=2, max = 15, message = "아이디는 2자 이상 15자 이하로 입력해주세요.")
    private String userId;

    @NotBlank(message = "이메일 주소를 입력해주세요.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    private String password;
}