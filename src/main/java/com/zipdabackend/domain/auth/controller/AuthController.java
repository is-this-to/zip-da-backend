package com.zipdabackend.domain.auth.controller;

import com.zipdabackend.domain.auth.request.RegistrationRequest;
import com.zipdabackend.domain.auth.service.AuthService;
import com.zipdabackend.global.response.GlobalResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    // User 회원가입
    @PostMapping("/users")
    public ResponseEntity<GlobalResponse<String>> registration(
            @Valid @RequestBody RegistrationRequest registrationRequest
    ) {
        authService.registration(registrationRequest);
        return ResponseEntity.status(200).body(
                GlobalResponse.<String>builder()
                        .code("00")
                        .message("회원가입이 정상 처리 됐습니다.")
                        .data("로그인에 성공했습니다")
                        .build()
        );
    }
}
