package com.zipdabackend.domain.auth.controller;

import com.zipdabackend.domain.admin.response.AdminResponse;
import com.zipdabackend.domain.auth.request.AdminLoginRequest;
import com.zipdabackend.domain.auth.response.AuthResponse;
import com.zipdabackend.domain.auth.service.AuthAdminService;
import com.zipdabackend.global.response.GlobalResponse;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
