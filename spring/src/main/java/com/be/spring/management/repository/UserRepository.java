package com.be.spring.management.repository;

import com.be.spring.management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // email로 사용자 정보 가져오기
    Optional<User> findByUserId(String userId); // userId로 사용자 정보 가져오기

    boolean existsByUserId(String userId); // userId가 이미 존재하는지 확인
}
