package com.project.auth.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    @DisplayName("JWT를 해석하고 유효성을 검증합니다.")
    void VerifyJwt() {
        // When
        String token = jwtService.generateAccessToken(userDetails);

        // Then
        assertDoesNotThrow(() -> jwtService.validateToken(token));
    }

    @Test
    @DisplayName("만료된 JWT는 검증에 실패해야 합니다.")
    void ExpiredJwtShouldFail() {
        // Given
        String mockIssuer = "testIssuer";
        String serverSecretKey = Base64.getEncoder().encodeToString("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdef".getBytes());

        String expiredToken = Jwts.builder()
                .subject(userDetails.getUsername())
                .issuer(mockIssuer)
                .issuedAt(new Date(System.currentTimeMillis() - 3600000)) // 1시간 전 발급
                .expiration(new Date(System.currentTimeMillis() - 1800000)) // 30분 전 만료
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(serverSecretKey)))
                .compact();

        // Then
        assertThrows(ExpiredJwtException.class, () -> jwtService.validateToken(expiredToken));
    }
}
