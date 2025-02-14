package com.project.auth.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtService {
    private final String issuer;
    private final Long expirationTime;
    private final SecretKey secretKey;

    public JwtService(
            @Value("${spring.application.name}") String issuer,
            @Value("${service.jwt.access-expiration}") Long expirationTime,
            @Value("${service.jwt.secret-key}") String secretKey
    ) {
        this.issuer = issuer;
        this.expirationTime = expirationTime;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String generateAccessToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername()) // 사용자 식별자
                .issuer(issuer) // JWT 발급자
                .issuedAt(new Date()) // JWT 발급 시간
                .expiration(new Date(System.currentTimeMillis() + expirationTime)) // JWT 만료 시간
                .signWith(secretKey) // JWT 서명 적용
                .compact(); // JWT 문자열 반환
    }

    public boolean validateToken(String token) {
        return false;
    }

    public String extractUsername(String token) {
        return null;
    }
}
