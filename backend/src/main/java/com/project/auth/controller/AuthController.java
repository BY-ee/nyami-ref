package com.project.auth.controller;

import com.project.auth.dto.SignupRequest;
import com.project.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping("/api")
@Tag(name = "인증 API", description = "인증 관련 API 명세서")
public class AuthController {
    private final AuthService authService;

    @Operation(
            summary = "회원가입",
            description = "회원 정보를 입력하여 새로운 계정을 생성합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "회원가입 성공",
                            content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "회원가입이 완료되었습니다."))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청 (입력값 검증 실패)",
                            content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "아이디는 필수 입력값입니다."))
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "중복된 계정 정보",
                            content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "중복된 아이디입니다."))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "서버 오류",
                            content = @Content(mediaType = "application/json", schema = @Schema(type = "string", example = "서버 내부 오류가 발생했습니다."))
                    )
            }
    )
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequest signupRequest) {
        authService.registerUser(signupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
    }
}
