package com.project.auth.service;

import com.project.auth.dto.SignupRequest;
import com.project.user.entity.Users;
import com.project.user.mapper.UserMapper;
import com.project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public void registerUser(SignupRequest signupRequest) {
        // 필수 입력값 예외처리
        if (signupRequest.getUsername() == null || signupRequest.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("아이디는 필수 입력값입니다.");
        }
        if (signupRequest.getNickname() == null || signupRequest.getNickname().trim().isEmpty()) {
            throw new IllegalArgumentException("닉네임은 필수 입력값입니다.");
        }
        if (signupRequest.getPassword() == null || signupRequest.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 필수 입력값입니다.");
        }
        if (signupRequest.getConfirmPassword() == null || signupRequest.getConfirmPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("비밀번호 확인은 필수 입력값입니다.");
        }
        if (signupRequest.getEmail() == null || signupRequest.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("이메일은 필수 입력값입니다.");
        }
        if (!signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        
        // 중복 데이터 예외처리
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new IllegalStateException("중복된 아이디입니다.");
        }
        if (userRepository.existsByNickname(signupRequest.getNickname())) {
            throw new IllegalStateException("중복된 닉네임입니다.");
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new IllegalStateException("중복된 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
        signupRequest.setPassword(encodedPassword);
        Users user = userMapper.toUser(signupRequest);
        userRepository.save(user);
    }
}
