package com.zipdabackend.domain.auth.service;

import com.zipdabackend.domain.admin.entity.Admin;
import com.zipdabackend.domain.admin.mapper.AdminMapper;
import com.zipdabackend.domain.admin.response.AdminResponse;
import com.zipdabackend.domain.auth.request.AdminLoginRequest;
import com.zipdabackend.domain.auth.response.AuthResponse;
import com.zipdabackend.global.constant.UserRole;
import com.zipdabackend.global.error.custom.NotRegisteredException;
import com.zipdabackend.global.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthAdminService {

    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    private AuthResponse<AdminResponse> generateAdminAuthentication(HttpServletResponse response, Admin admin) {
        String newAccessToken = jwtProvider.generateAdminAccessToken(admin);
        return AuthResponse.<AdminResponse>builder()
                .accessToken(newAccessToken)
                .principal(
                        AdminResponse.builder()
                                .adminId(admin.getAdminId())
                                .name(admin.getName())
                                .role(UserRole.ADMIN)
                                .build()
                )
                .build();
    }


    public AuthResponse<AdminResponse> loginAdmin(HttpServletResponse response, AdminLoginRequest adminLoginRequest) {
        Admin findByEmailAdmin = adminMapper.findbyCode(adminLoginRequest.adminCode());

        if(findByEmailAdmin == null) {
            throw new NotRegisteredException("관리자 코드 또는 비밀번호가 일치하지 않습니다.");
        }

        if(!passwordEncoder.matches(adminLoginRequest.password(), findByEmailAdmin.getPassword())) {
            throw new NotRegisteredException("관리자 코드 또는 비밀번호가 일치하지 않습니다.");
        }
        return this.generateAdminAuthentication(response, findByEmailAdmin);
    }
}
