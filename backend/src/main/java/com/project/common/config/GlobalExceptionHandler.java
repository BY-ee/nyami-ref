package com.project.common.config;

import com.project.common.exception.InvalidJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** 예외를 처리하는 전역 예외 처리 클래스 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /** AuthenticationException 예외 처리 */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        log.warn("Authentication 예외가 발생하였습니다: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    /** InvalidJwtException 예외 처리 */
    @ExceptionHandler(InvalidJwtException.class)
    public ResponseEntity<String> handleInvalidJwtException(InvalidJwtException e) {
        log.warn("InvalidJwt 예외가 발생하였습니다: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    /** ExpiredJwtException 예외 처리 */
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException e) {
        log.warn("ExpiredJwt 예외가 발생하였습니다: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰이 만료되었습니다.");
    }

    /** JwtException 예외 처리 */
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<String> handleJwtException(JwtException e) {
        log.warn("Jwt 예외가 발생하였습니다: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰입니다.");
    }

    /** IllegalArgumentException 예외 처리 */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("IllegalArgument 예외가 발생하였습니다: {}", e.getMessage(), e);
        return ResponseEntity.badRequest().body("입력값이 잘못되었습니다.");
    }

    /** IllegalStateException 예외 처리 */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException e) {
        log.warn("IllegalState 예외가 발생하였습니다: {}", e.getMessage(), e);
        return ResponseEntity.badRequest().body("요청을 처리할 수 없는 상태입니다.");
    }

    /** 일반 예외 처리 */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception e) {
        log.warn("예외가 발생하였습니다: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 내부 오류가 발생했습니다.");
    }
}
