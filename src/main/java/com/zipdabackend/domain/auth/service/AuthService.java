package com.zipdabackend.domain.auth.service;

import com.zipdabackend.domain.auth.mapper.AuthMapper;
import com.zipdabackend.domain.auth.request.LoginRequest;
import com.zipdabackend.domain.auth.request.RegistrationRequest;
import com.zipdabackend.domain.auth.response.AuthResponse;
import com.zipdabackend.domain.user.entity.User;
import com.zipdabackend.domain.user.mapper.UserMapper;
import com.zipdabackend.domain.user.response.UserResponse;
import com.zipdabackend.global.constant.UserRole;
import com.zipdabackend.global.cookie.CookieManager;
import com.zipdabackend.global.error.custom.auth.*;
import com.zipdabackend.global.jwt.JwtConfig;
import com.zipdabackend.global.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CookieManager cookieManager;
    private final JwtConfig jwtConfig;

    // 토큰 재발급
    private AuthResponse<UserResponse> generateAuthentication(HttpServletResponse response, User user) {
        // 토큰 생성
        String newAccessToken = jwtProvider.generateAccessToken(user);
        String newRefreshToken = jwtProvider.generateRefreshToken(user);

        int updateRefreshUserNum = authMapper.updateRefreshToken(user.getUserId(), newRefreshToken);

        if(updateRefreshUserNum != 1) {
            throw new AuthenticationFailedException("인증 처리 중 오류가 발생했습니다.");
        }

        // 새로 만든 refreshToken response 헤더에 저장, refreshToken은 reissue할때만 보냄
        cookieManager.setCookie(response, jwtConfig.userRefreshTokenCookieName(), newRefreshToken, jwtConfig.refreshTokenCookieExpiry(), jwtConfig.refreshTokenCookiePath());

        return AuthResponse.<UserResponse>builder()
                .accessToken(newAccessToken)
                .principal(
                        UserResponse.builder()
                                .userId(user.getUserId())
                                .email(user.getEmail())
                                .name(user.getName())
                                .nick(user.getNick())
                                .phone(user.getPhone())
                                .role(user.getRole())
                                .createdAt(user.getCreatedAt())
                                .build()
                )
                .build();
    }

    // user 로그인
    public AuthResponse<UserResponse> login(LoginRequest loginRequest, HttpServletResponse response) {
        User findByEmailUser = userMapper.findByEmail(loginRequest.email());

        if(findByEmailUser == null) {
            throw new NotRegisteredException("해당 이메일을 가진 유저가 없거나 비밀번호가 틀렸습니다");
        }

        if(!passwordEncoder.matches(loginRequest.password(), findByEmailUser.getPassword())) {
            throw new NotRegisteredException("해당 이메일을 가진 유저가 없거나 비밀번호가 틀렸습니다");
        }
        return this.generateAuthentication(response, findByEmailUser);
    }

    //user 로그아웃
    public void logout(HttpServletResponse response, Long userId) {
        User user = userMapper.findByPk(userId);

        // 일치한 user가 없음
        if(user == null) {
            throw new TokenException("유효하지 않은 인증 토큰입니다.");
        }

        // DB에 저장된 refreshToken null로
        authMapper.updateRefreshToken(userId, null);

        // Cookie에 저장한 리프레시 토큰 파기 -> maxAge 0으로 설정해서 브라우저가 쿠키 받고 없애게함
        cookieManager.setCookie(response, jwtConfig.userRefreshTokenCookieName(), null, 0, jwtConfig.refreshTokenCookiePath());
    }

    // 회원가입
    public void registration(RegistrationRequest registrationRequest) {
        // 겹치는 email과 nick을 가지고 있는 user 찾고 있으면 error 반환
        User findByEmailUser = userMapper.findByEmail(registrationRequest.email());
        User findByNickUser = userMapper.findByNick(registrationRequest.nick());
        if(findByEmailUser != null) {
            throw new DuplicateEmailException("이미 가입된 이메일입니다.");
        }
        if(findByNickUser != null) {
            throw new DuplicateNickException("이미 사용된 닉네임입니다.");
        }
        User newUser = User.builder()
                .name(registrationRequest.name())
                .phone(registrationRequest.phone())
                .password(passwordEncoder.encode(registrationRequest.password()))
                .email(registrationRequest.email())
                .nick(registrationRequest.nick())
                .build();
        try {
            int insertUserNum = authMapper.insertUser(newUser);
            // insert한 유저의 레코드 수가 이상함
            if (insertUserNum != 1) {
                throw new UserRegistrationFailedException("회원가입 처리에 실패했습니다.");
            }
        // insert 사이에 동일한 이메일, 닉네임을 가진 새로운 유저가 가입함
        } catch (DuplicateKeyException e) {
            throw new DuplicateUserException("이미 가입된 이메일 또는 닉네임입니다.");
        }
    }

    // 토큰 재발급
    public AuthResponse<UserResponse> reissueToken (HttpServletRequest request, HttpServletResponse response) {
        Optional<String> extractRefreshTokenFromRequest = jwtProvider.extractRefreshToken(request, jwtConfig.userRefreshTokenCookieName());
        if(extractRefreshTokenFromRequest.isEmpty()) {
            throw new TokenException("refreshToken이 없습니다.");
        }
        String extractRefreshToken = extractRefreshTokenFromRequest.get();
        Claims claims = jwtProvider.extractClaims(extractRefreshToken);

        if(!"REFRESH".equals(claims.get("tokenType", String.class))) {
            throw new TokenException("Refresh Token이 아닙니다.");
        }
        String role = claims.get("role", String.class);
        if(!UserRole.USER.name().equals(role) && !UserRole.AGENT.name().equals(role)) {
            throw new TokenException("사용자 Refresh Token이 아닙니다.");
        }

        long userId;
        try {
            userId = Long.parseLong(claims.getSubject());
        } catch (NumberFormatException e) {
            throw new TokenException("유효하지 않은 subject입니다.");
        }

        User user = userMapper.findByPk(userId);

        if(user == null || user.getRefreshToken() == null) {
            throw new TokenException("유효하지 않은 회원의 토큰입니다.");
        }

        if(!user.getRefreshToken().equals(extractRefreshToken)) {
            throw new TokenException("토큰이 일치하지 않습니다");
        }
        return this.generateAuthentication(response, user);
    }
}
