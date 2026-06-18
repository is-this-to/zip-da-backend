package com.zipdabackend.global.security;

import com.zipdabackend.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SecurityAuthenticationProvider {
    private final JwtProvider jwtProvider;

    public Authentication authentication(String token) {
        return new UsernamePasswordAuthenticationToken(
                jwtProvider.extractClaims(token),   // 1. Principal (사용자 정보) -> Claims 넣어놓음
                null,                               // 2. Credentials (비밀번호, jwt는 보통 null)
                List.of()                           // 3. Authorities (권한 목록)
        );
    }
}
