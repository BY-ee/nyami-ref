package com.project.auth.jwt;

import com.project.auth.dto.CustomUserDetails;
import com.project.common.exception.InvalidJwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JwtTest {
    private JwtService jwtService;

    private String validToken; // 유효한 토큰
    private String expiredToken; // 만료된 토큰
    private String invalidToken; // 변조된 토큰
    private Authentication authentication; // 인증 정보 관리 객체
    private UserDetails userDetails; // 인증 유저 데이터
    private String issuer; // 토큰 발급자
    private SecretKey secretKey; // 비밀 키

    @BeforeEach
    void setUp() {
        issuer = "testIssuer";
        String encodedKey = Base64.getEncoder().encodeToString("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdef".getBytes()); // 인코딩된 서버의 비밀 키
        secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(encodedKey));

        jwtService = new JwtService(issuer, 3600000L, encodedKey);
        userDetails = new CustomUserDetails(1,"test","password123!", List.of(new SimpleGrantedAuthority("ROLE_USER")));

        authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        validToken = jwtService.generateAccessToken(authentication);
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
    void createJwt() {
        // Given
        String token = jwtService.generateAccessToken(authentication);

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
    void verifyJwt() {
        // Then
        assertDoesNotThrow(() -> jwtService.validateToken(validToken));
    }

    @Test
    @DisplayName("만료된 JWT는 검증에 실패해야 합니다.")
    void verifyExpiredJwt() {
        // Then
        assertThrows(InvalidJwtException.class, () -> jwtService.validateToken(expiredToken));
    }

    @Test
    @DisplayName("변조된 JWT는 검증에 실패해야 합니다.")
    void verifyInvalidJwt() {
        // Then
        assertThrows(InvalidJwtException.class, () -> jwtService.validateToken(invalidToken));
    }

    @Test
    @DisplayName("유효한 JWT에서 사용자명을 추출합니다.")
    void extractUsernameWithValidToken() {
        // Given
        String username = jwtService.extractUsername(validToken);

        // Then
        assertEquals("test", username);
    }

    @Test
    @DisplayName("만료된 JWT에서 사용자명을 추출하면 예외가 발생해야 합니다.")
    void extractUsernameWithExpiredToken() {
        // Then
        assertThrows(ExpiredJwtException.class, () -> jwtService.extractUsername(expiredToken));
    }

    @Test
    @DisplayName("변조된 JWT에서 사용자명을 추출하면 예외가 발생해야 합니다.")
    void extractUsernameWithInvalidToken() {
        // Then
        assertThrows(JwtException.class, () -> jwtService.extractUsername(invalidToken));
    }

    @Test
    @DisplayName("유효한 JWT에서 만료 시간을 추출합니다.")
    void extractExpirationTimeWithValidToken() {
        // Given
        Date expirationDate = jwtService.extractExpirationDate(validToken);

        // Then
        assertNotNull(expirationDate);
    }

    @Test
    @DisplayName("만료된 JWT에서 만료 시간을 추출하면 예외가 발생해야 합니다.")
    void extractExpirationTimeWithExpiredToken() {
        // Then
        assertThrows(ExpiredJwtException.class, () -> jwtService.extractExpirationDate(expiredToken));
    }

    @Test
    @DisplayName("변조된 JWT에서 만료 시간을 추출하면 예외가 발생해야 합니다.")
    void extractExpirationTimeWithInvalidToken() {
        // Then
        assertThrows(JwtException.class, () -> jwtService.extractExpirationDate(invalidToken));
    }
}
