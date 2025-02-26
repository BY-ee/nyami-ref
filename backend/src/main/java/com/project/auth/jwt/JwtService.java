package com.project.auth.jwt;

import com.project.auth.dto.CustomUserDetails;
import com.project.common.exception.InvalidJwtException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
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

    // Spring 컨텍스트의 유저 데이터로 토큰을 생성하는 메서드
    public String generateAccessToken(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .subject(userDetails.getUsername()) // 사용자 식별자
                .issuer(issuer) // JWT 발급자
                .issuedAt(new Date()) // JWT 발급 시간
                .expiration(new Date(System.currentTimeMillis() + expirationTime)) // JWT 만료 시간
                .claim("id", userDetails.getId()) // 사용자 ID
                .signWith(secretKey) // JWT 서명 적용
                .compact(); // JWT 문자열 반환
    }

    // 토큰의 유효성을 검증하는 메서드
    public void validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
        } catch (ExpiredJwtException e) {
            throw new InvalidJwtException("JWT 토큰이 만료되었습니다.", e);
        } catch (MalformedJwtException e) {
            throw new InvalidJwtException("JWT 토큰이 변조되었습니다.", e);
        } catch (SecurityException e) {
            throw new InvalidJwtException("JWT 서명이 유효하지 않습니다.", e);
        } catch (JwtException e) {
            throw new InvalidJwtException("JWT 토큰이 유효하지 않습니다.", e); // 이외의 예외
        }
    }


    // JWT에서 사용자명을 추출하는 메서드
    public String extractUsername(String token) {
        return getPayload(token).getSubject();
    }

    // JWT에서 만료 시간을 추출하는 메서드
    public Date extractExpirationDate(String token) {
        return getPayload(token).getExpiration();
    }

    // JWT의 payload를 반환하는 메서드
    private Claims getPayload(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
