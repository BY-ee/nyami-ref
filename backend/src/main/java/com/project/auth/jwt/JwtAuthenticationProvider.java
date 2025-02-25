package com.project.auth.jwt;

import com.project.auth.dto.CustomUserDetails;
import com.project.auth.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Authentication의 JWT 추출 후 검증
        String token = authentication.getCredentials().toString();
        jwtService.validateToken(token);

        // JWT에서 사용자 정보 추출
        String username = jwtService.extractUsername(token);
        CustomUserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // 인증 객체 반환 (비밀번호 null)
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
