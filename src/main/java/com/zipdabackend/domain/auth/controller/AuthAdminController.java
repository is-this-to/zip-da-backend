package com.zipdabackend.domain.auth.controller;

import com.zipdabackend.domain.admin.response.AdminResponse;
import com.zipdabackend.domain.auth.request.AdminLoginRequest;
import com.zipdabackend.domain.auth.response.AuthResponse;
import com.zipdabackend.domain.auth.service.AuthAdminService;
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
@RequestMapping("/api/admin")
public class AuthAdminController {

    private final AuthAdminService authAdminService;

    @PostMapping("/auth/sessions")
    public ResponseEntity<GlobalResponse<AuthResponse<AdminResponse>>> loginAdmin(@Valid @RequestBody AdminLoginRequest adminLoginRequest, HttpServletResponse response) {
        AuthResponse<AdminResponse> adminLogResponse = authAdminService.loginAdmin(response, adminLoginRequest);
        return ResponseEntity.status(200).body(
                GlobalResponse.<AuthResponse<AdminResponse>>builder()
                        .code("00")
                        .message("정상 처리")
                        .data(adminLogResponse)
                        .build()
        );
    }

    @PostMapping("auth/tokens")
    public ResponseEntity<GlobalResponse<AuthResponse<AdminResponse>>> adminReissue(HttpServletResponse response, HttpServletRequest request) {
        AuthResponse<AdminResponse> adminLogResponse = authAdminService.adminReissue(request, response);
        return ResponseEntity.status(200).body(
                GlobalResponse.<AuthResponse<AdminResponse>>builder()
                        .code("00")
                        .message("정상 처리")
                        .data(adminLogResponse)
                        .build()
        );
    }

    @DeleteMapping("/auth/sessions")
    public ResponseEntity<GlobalResponse<String>> logoutAdmin(@AuthenticationPrincipal Claims claims, HttpServletResponse response) {
        authAdminService.logout(response, Long.parseLong(claims.getSubject()));
        return ResponseEntity.status(200).body(
                GlobalResponse.<String>builder()
                        .code("00")
                        .message("정상 처리")
                        .data("admin 로그아웃")
                        .build()
        );
    }
}
