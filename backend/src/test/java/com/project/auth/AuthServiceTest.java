package com.project.auth;

import com.project.auth.dto.SignupRequest;
import com.project.auth.service.AuthService;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class AuthServiceTest {
    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @Test
    @DisplayName("알맞은 정보를 입력한 후 회원가입을 요청하면 데이터베이스에 저장한다.")
    void signup() {
        // given
        SignupRequest request = new SignupRequest();
        request.setUsername("test");
        request.setNickname("nick");
        request.setPassword("password123!");
        request.setConfirmPassword("password123!");
        request.setEmail("test@test.com");

        Users user = new Users();
        user.setUsername("test");
        user.setNickname("nick");
        user.setPassword("encoded_password");
        user.setEmail("test@test.com");

        when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");
        when(userMapper.toUser(request)).thenReturn(user);
        when(userRepository.save(any(Users.class))).thenReturn(user);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        // when
        authService.registerUser(request);

        // then
        Optional<Users> savedUser = userRepository.findById(1L);
        assertTrue(savedUser.isPresent());
        assertEquals("test", savedUser.get().getUsername());
        assertEquals("test@test.com", savedUser.get().getEmail());
        assertEquals("nick", savedUser.get().getNickname());
    }
}
