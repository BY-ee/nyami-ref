package com.project.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.auth.dto.LoginRequest;
import com.project.auth.jwt.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthApiTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("로그인을 성공하면 정상적으로 JWT를 반환합니다.")
    void login() throws Exception {
        // Given
        LoginRequest loginRequest = new LoginRequest("test", "password123!");

        // When & Then
        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists());
    }

    @Test
    @DisplayName("만료된 JWT를 사용하면 401 응답을 반환합니다.")
    void loginWithExpiredJwt() throws Exception {
        // Given
        String encodedKey = Base64.getEncoder().encodeToString("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdef".getBytes()); // 인코딩된 서버의 비밀 키
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(encodedKey));

        String expiredToken = Jwts.builder()
                .subject("test")
                .issuer("testIssuer")
                .issuedAt(new Date(System.currentTimeMillis() - 3600000)) // 1시간 전 발급
                .expiration(new Date(System.currentTimeMillis() - 1800000)) // 30분 전 만료
                .signWith(secretKey)
                .compact();

        // When & Then
        mockMvc.perform(get("/api/stores")
                        .header("Authorization", "Bearer " + expiredToken))
                .andExpect(status().isUnauthorized());
    }
}
