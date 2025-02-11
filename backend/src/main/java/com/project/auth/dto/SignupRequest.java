package com.project.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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

    @Schema(description = "사용자 아이디", example = "test")
    @NotBlank(message = "아이디는 필수 입력값입니다.")
    private String username;

    @Schema(description = "닉네임", example = "nick")
    @NotBlank(message = "닉네임은 필수 입력값입니다.")
    private String nickname;

    @Schema(description = "비밀번호", example = "password123!")
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;

    @Schema(description = "비밀번호 확인", example = "password123!")
    @NotBlank(message = "비밀번호 확인은 필수 입력값입니다.")
    private String confirmPassword;

    @Schema(description = "이메일", example = "test@test.com")
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    private String email;
}
