package com.project.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 요청의 Authorization 헤더에서 JWT 추출
        String token = null;
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.replace("Bearer ", "");
        }

        // 2. 토큰이 존재할 경우 인증 시도
        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                // authenticationManager를 통해 JWT 검증
                Authentication authentication = authenticationManager.authenticate(new JwtAuthenticationToken(token));

                // 인증이 통과하면 컨텍스트에 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (AuthenticationException e) {
                SecurityContextHolder.clearContext(); // 인증 실패시 컨텍스트 초기화
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 유효하지 않습니다.");
                return;
            }
        }

        // 3. 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }
}
