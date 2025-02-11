package com.project.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "회원가입 요청 Dto")
public class SignupRequest {
    private String username;
    private String nickname;
    private String password;
    private String confirmPassword;
    private String email;
}
