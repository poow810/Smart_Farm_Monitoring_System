package com.be.spring.management.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank
    @Size(min=2, max = 15, message = "아이디는 2자 이상 15자 이하로 입력해주세요.")
    @Column(name = "userId", nullable = false, unique = true)
    private String userId;

    @NotBlank(message = "이메일 주소를 입력해주세요.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    @Column(name = "password")
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

//    // OAuth 서비스
//    // 사용자 이름
//    @Column(name = "nickname", unique = true)
//    private String nickname;

//    @Builder
//    public User(String email, String password,  role) {
//        this.email = email;
//        this.password = password;
//        this.roles = roles;
//    }
//
//    public void addDevice(Device device) {
//        devices.add(device);
//        device.setUser(this);
//    }
//
//    public User update(String nickname) {
//        this.nickname = nickname;
//        return this;
//    }

    @Override // 사용자의 id를 반환(고유 값)
    public String getUsername() {
        return email;
    }

    @Override // 사용자의 패스워드 반환
    public String getPassword() {
        return password;
    }

    @Override // 계정 만료 여부 반환
    public boolean isAccountNonExpired() {
        return true; // true = 만료되지 않음
    }

    @Override // 계정 잠금 여부 반환
    public boolean isAccountNonLocked() {
        return true; // true = 잠금되지 않음
    }

    @Override // 패스워드의 만료 여부 반환
    public boolean isCredentialsNonExpired() {
        return true; // true = 만료되지 않음
    }

    @Override // 계정 사용 가능 여부 반환
    public boolean isEnabled() {
        return true; // true = 사용 가능
    }
}
