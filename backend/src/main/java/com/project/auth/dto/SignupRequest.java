package com.project.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "회원가입 요청 Dto")
public class SignupRequest {
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String nickname;
}
