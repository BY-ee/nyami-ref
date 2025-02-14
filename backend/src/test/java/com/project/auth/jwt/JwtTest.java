package com.project.auth.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JwtTest {
    private JwtService jwtService;
    private UserDetails userDetails; // 유저 데이터

    @BeforeEach
    void setUp() {
        String mockIssuer = "testIssuer"; // 토큰 발급자
        Long mockExpirationTime = 1000 * 60 * 60L; // 토큰 만료시간
        String serverSecretKey = Base64.getEncoder().encodeToString("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdef".getBytes()); // 인코딩된 서버의 비밀 키

        jwtService = new JwtService(mockIssuer, mockExpirationTime, serverSecretKey);
        userDetails = User.withUsername("test")
                .password("password123!")
                .roles("USER")
                .build();
    }

    @Test
    @DisplayName("JWT를 생성합니다.")
    void CreateJwt() {
        // When
        String token = jwtService.generateAccessToken(userDetails);

        // Then
        assertNotNull(token);
    }
}
