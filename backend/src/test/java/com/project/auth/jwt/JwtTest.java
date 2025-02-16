package com.project.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class JwtTest {
    private JwtService jwtService;

    private String validToken; // 유효한 토큰
    private String expiredToken; // 만료된 토큰
    private String invalidToken; // 변조된 토큰
    private UserDetails userDetails; // 유저 데이터
    private String issuer; // 토큰 발급자
    private SecretKey secretKey; // 비밀 키

    @BeforeEach
    void setUp() {
        issuer = "testIssuer";
        String encodedKey = Base64.getEncoder().encodeToString("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdef".getBytes()); // 인코딩된 서버의 비밀 키
        secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(encodedKey));

        jwtService = new JwtService(issuer, 3600000L, encodedKey);
        userDetails = User.withUsername("test")
                .password("password123!")
                .roles("USER")
                .build();

        validToken = jwtService.generateAccessToken(userDetails);
        expiredToken = Jwts.builder()
                .subject(userDetails.getUsername())
                .issuer(issuer)
                .issuedAt(new Date(System.currentTimeMillis() - 3600000)) // 1시간 전 발급
                .expiration(new Date(System.currentTimeMillis() - 1800000)) // 30분 전 만료
                .signWith(secretKey)
                .compact();
        invalidToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJzdWIiOiJ0ZXN0VXNlciIsImlzcyI6InRlc3RJc3N1ZXIiLCJpYXQiOjE2MDAwMDAwMDAsImV4cCI6MTYwMDAwMDAwMH0" +
                ".fake-signature";
    }

    @Test
    @DisplayName("JWT를 생성합니다.")
    void CreateJwt() {
        // Given
        String token = jwtService.generateAccessToken(userDetails);

        // When
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        // Then
        assertNotNull(token); // 토큰 생성 여부 확인
        assertEquals(userDetails.getUsername(), claims.getSubject()); // subject가 올바른지 확인
        assertEquals(issuer, claims.getIssuer()); // issuer가 올바른지 확인
        assertNotNull(claims.getExpiration()); // 만료 시간 포함 여부 확인
    }

    @Test
    @DisplayName("유효한 JWT를 해석하고 유효성을 검증합니다.")
    void VerifyJwt() {
        // Then
        assertDoesNotThrow(() -> jwtService.validateToken(validToken));
    }

    @Test
    @DisplayName("만료된 JWT는 검증에 실패해야 합니다.")
    void ExpiredJwtShouldFail() {
        // Then
        assertThrows(ExpiredJwtException.class, () -> jwtService.validateToken(expiredToken));
    }

    @Test
    @DisplayName("변조된 JWT는 검증에 실패해야 합니다.")
    void InvalidJwtShouldFail() {
        // Then
        assertThrows(SignatureException.class, () -> jwtService.validateToken(invalidToken));
    }
}
