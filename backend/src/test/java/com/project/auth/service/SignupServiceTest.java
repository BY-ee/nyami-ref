package com.project.auth.service;

import com.project.auth.dto.SignupRequest;
import com.project.user.entity.Users;
import com.project.user.mapper.UserMapper;
import com.project.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class SignupServiceTest {
    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @Test
    @DisplayName("알맞은 정보를 입력하면 회원 정보를 저장한다.")
    void signup() {
        // Given
        SignupRequest request = new SignupRequest("test", "nick", "password123!", "password123!", "test@test.com");

        Users user = new Users();
        user.setUsername("test");
        user.setNickname("nick");
        user.setPassword("encoded_password");
        user.setEmail("test@test.com");

        when(passwordEncoder.encode("password123!")).thenReturn("encoded_password");
        when(userMapper.toUser(request)).thenReturn(user);
        when(userRepository.save(any(Users.class))).thenReturn(user);

        // When
        authService.registerUser(request);

        // Then
        verify(passwordEncoder, times(1)).encode("password123!");
        verify(userRepository, times(1)).save(any(Users.class));
    }

    @Test
    @DisplayName("유저 아이디에 빈 값이 전달되면 예외를 발생시킨다.")
    void signupWithoutUsername() {
        // Given
        SignupRequest request = new SignupRequest("", "nick", "password123!", "password123!", "test@test.com");

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> authService.registerUser(request));
        assertEquals("아이디는 필수 입력값입니다.", exception.getMessage());
        verify(userRepository, never()).save(any(Users.class));
    }

    @Test
    @DisplayName("닉네임에 빈 값이 전달되면 예외를 발생시킨다.")
    void signupWithoutNickname() {
        // Given
        SignupRequest request = new SignupRequest("test", "", "password123!", "password123!", "test@test.com");

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> authService.registerUser(request));
        assertEquals("닉네임은 필수 입력값입니다.", exception.getMessage());
        verify(userRepository, never()).save(any(Users.class));
    }

    @Test
    @DisplayName("비밀번호에 빈 값이 전달되면 예외를 발생시킨다.")
    void signupWithoutPassword() {
        // Given
        SignupRequest request = new SignupRequest("test", "nick", "", "password123!", "test@test.com");

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> authService.registerUser(request));
        assertEquals("비밀번호는 필수 입력값입니다.", exception.getMessage());
        verify(userRepository, never()).save(any(Users.class));
    }

    @Test
    @DisplayName("비밀번호 확인에 빈 값이 전달되면 예외를 발생시킨다.")
    void signupWithoutConfirmPassword() {
        // Given
        SignupRequest request = new SignupRequest("test", "nick", "password123!", "", "test@test.com");

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> authService.registerUser(request));
        assertEquals("비밀번호 확인은 필수 입력값입니다.", exception.getMessage());
        verify(userRepository, never()).save(any(Users.class));
    }

    @Test
    @DisplayName("이메일에 빈 값이 전달되면 예외를 발생시킨다.")
    void signupWithoutEmail() {
        // Given
        SignupRequest request = new SignupRequest("test", "nick", "password123!", "password123!", "");

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> authService.registerUser(request));
        assertEquals("이메일은 필수 입력값입니다.", exception.getMessage());
        verify(userRepository, never()).save(any(Users.class));
    }

    @Test
    @DisplayName("비밀번호와 비밀번호 확인이 다르면 예외를 발생시킨다.")
    void signupWithPasswordMismatch() {
        // Given
        SignupRequest request = new SignupRequest("test", "nick", "password123!", "pass", "test@test.com");

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> authService.registerUser(request));
        assertEquals("비밀번호가 일치하지 않습니다.", exception.getMessage());
        verify(userRepository, never()).save(any(Users.class));
    }

    @Test
    @DisplayName("중복된 아이디로 가입을 시도하면 예외를 발생시킨다.")
    void signupWithDuplicateUsername() {
        // Given
        SignupRequest request = new SignupRequest("test", "nick", "password123!", "password123!", "test@test.com");
        when(userRepository.existsByUsername(request.getUsername())).thenReturn(true);

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> authService.registerUser(request));
        assertEquals("중복된 아이디입니다.", exception.getMessage());
        verify(userRepository, never()).save(any(Users.class));
    }

    @Test
    @DisplayName("중복된 닉네임으로 가입을 시도하면 예외를 발생시킨다.")
    void signupWithDuplicateNickname() {
        // Given
        SignupRequest request = new SignupRequest("test", "nick", "password123!", "password123!", "test@test.com");
        when(userRepository.existsByNickname(request.getNickname())).thenReturn(true);

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> authService.registerUser(request));
        assertEquals("중복된 닉네임입니다.", exception.getMessage());
        verify(userRepository, never()).save(any(Users.class));
    }

    @Test
    @DisplayName("중복된 이메일로 가입을 시도하면 예외를 발생시킨다.")
    void signupWithDuplicateEmail() {
        // Given
        SignupRequest request = new SignupRequest("test", "nick", "password123!", "password123!", "test@test.com");
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // When & Then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> authService.registerUser(request));
        assertEquals("중복된 이메일입니다.", exception.getMessage());
        verify(userRepository, never()).save(any(Users.class));
    }
}
