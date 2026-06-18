package com.zipdabackend.domain.auth.controller;

import com.zipdabackend.domain.auth.request.LoginRequest;
import com.zipdabackend.domain.auth.request.RegistrationRequest;
import com.zipdabackend.domain.auth.response.AuthResponse;
import com.zipdabackend.domain.auth.service.AuthService;
import com.zipdabackend.global.response.GlobalResponse;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    // user 로그인
    @PostMapping("/auth/sessions")
    public ResponseEntity<GlobalResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return ResponseEntity.status(200).body(
                GlobalResponse.<AuthResponse>builder()
                        .code("00")
                        .message("로그인이 정상 처리 됐습니다.")
                        .data(authService.login(loginRequest, response))
                        .build()
        );
    }

    // user 로그아웃
    @DeleteMapping("/auth/sessions")
    public ResponseEntity<GlobalResponse<String>> logout(@AuthenticationPrincipal Claims claims, HttpServletResponse response) {
        authService.logout(response, Long.parseLong(claims.getSubject()));
        return ResponseEntity.status(200).body(
                GlobalResponse.<String>builder()
                        .code("00")
                        .message("로그아웃이 정상 처리 됐습니다.")
                        .build()
        );
    }

    // user 회원가입
    @PostMapping("/users")
    public ResponseEntity<GlobalResponse<String>> registration(@Valid @RequestBody RegistrationRequest registrationRequest) {
        authService.registration(registrationRequest);
        return ResponseEntity.status(201).body(
                GlobalResponse.<String>builder()
                        .code("00")
                        .message("회원가입이 정상 처리 됐습니다.")
                        .data("회원가입에 성공했습니다.")
                        .build()
        );
    }

    // accessToken 재발급
    @PostMapping("/auth/tokens")
    public ResponseEntity<GlobalResponse<AuthResponse>> reissueToken(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.status(200).body(
                GlobalResponse.<AuthResponse>builder()
                        .code("00")
                        .message("정상 처리 됐습니다.")
                        .data(authService.reissueToken(request, response))
                        .build()
        );
    }
}
