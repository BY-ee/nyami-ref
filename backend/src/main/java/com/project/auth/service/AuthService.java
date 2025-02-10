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
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
        signupRequest.setPassword(encodedPassword);
        Users user = userMapper.toUser(signupRequest);
        userRepository.save(user);
    }
}
