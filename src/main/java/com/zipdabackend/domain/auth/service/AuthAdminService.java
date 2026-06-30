package com.zipdabackend.domain.auth.service;

import com.zipdabackend.domain.admin.entity.Admin;
import com.zipdabackend.domain.admin.mapper.AdminMapper;
import com.zipdabackend.domain.admin.response.AdminResponse;
import com.zipdabackend.domain.auth.mapper.AuthMapper;
import com.zipdabackend.domain.auth.request.AdminLoginRequest;
import com.zipdabackend.domain.auth.response.AuthResponse;
import com.zipdabackend.global.constant.UserRole;
import com.zipdabackend.global.cookie.CookieManager;
import com.zipdabackend.global.error.custom.auth.AuthenticationFailedException;
import com.zipdabackend.global.error.custom.auth.NotRegisteredException;
import com.zipdabackend.global.error.custom.auth.TokenException;
import com.zipdabackend.global.jwt.JwtConfig;
import com.zipdabackend.global.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthAdminService {

    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthMapper authMapper;
    private final JwtConfig jwtConfig;
    private final CookieManager cookieManager;

    private AuthResponse<AdminResponse> generateAdminAuthentication(HttpServletResponse response, Admin admin) {
        String newAccessToken = jwtProvider.generateAdminAccessToken(admin);
        String newRefreshToken = jwtProvider.generateAdminRefreshToken(admin);

        int updateRefreshUserNum = authMapper.updateAdminRefreshToken(admin.getAdminId(), newRefreshToken);

        if(updateRefreshUserNum != 1) {
            throw new AuthenticationFailedException("인증 처리 중 오류가 발생했습니다.");
        }

        cookieManager.setCookie(response, jwtConfig.refreshTokenCookieName(), newRefreshToken, jwtConfig.refreshTokenCookieExpiry(), jwtConfig.adminRefreshTokenCookiePath());

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
        Admin findByEmailAdmin = adminMapper.findByCode(adminLoginRequest.adminCode());

        if(findByEmailAdmin == null) {
            throw new NotRegisteredException("관리자 코드 또는 비밀번호가 일치하지 않습니다.");
        }

        if(!passwordEncoder.matches(adminLoginRequest.password(), findByEmailAdmin.getPassword())) {
            throw new NotRegisteredException("관리자 코드 또는 비밀번호가 일치하지 않습니다.");
        }
        return this.generateAdminAuthentication(response, findByEmailAdmin);
    }

    public void logout(HttpServletResponse response, long adminId) {
        Admin findByIdAdmin = adminMapper.findByAdminId(adminId);
        if (findByIdAdmin == null) {
            throw new TokenException("유효하지 않은 인증 토큰 입니다.");
        }

        // DB에 저장된 refreshToken null
        authMapper.updateAdminRefreshToken(adminId, null);

        // Cookie에 저장된 리프래시 토큰 파기
        cookieManager.setCookie(response, jwtConfig.refreshTokenCookieName(), null, 0, jwtConfig.adminRefreshTokenCookiePath());
    }

    // admin 재발급
    public AuthResponse<AdminResponse> adminReissue (HttpServletRequest request, HttpServletResponse response) {
        Optional<String> extractAdminRefreshTokenFromRequest = jwtProvider.extractRefreshToken(request);
        if(extractAdminRefreshTokenFromRequest.isEmpty()) {
            throw new TokenException("refreshToken이 없습니다.");
        }
        String extractRefreshToken = extractAdminRefreshTokenFromRequest.get();
        Claims claims = jwtProvider.extractClaims(extractRefreshToken);

        if(!"REFRESH".equals(claims.get("tokenType", String.class))) {
            throw new TokenException("Refresh Token이 아닙니다.");
        }

        long adminId;
        try {
            adminId = Long.parseLong(claims.getSubject());
        } catch (NumberFormatException e) {
            throw new TokenException("유효하지 않은 subject입니다.");
        }

        Admin admin = adminMapper.findByAdminId(adminId);

        if(admin == null || admin.getRefreshToken() == null) {
            throw new TokenException("유효하지 않은 회원의 토큰입니다.");
        }

        if(!admin.getRefreshToken().equals(extractRefreshToken)) {
            throw new TokenException("토큰이 일치하지 않습니다");
        }
        return this.generateAdminAuthentication(response, admin);
    }
}
