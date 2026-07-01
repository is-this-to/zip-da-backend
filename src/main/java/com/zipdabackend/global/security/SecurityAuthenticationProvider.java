package com.zipdabackend.global.security;

import com.zipdabackend.global.error.custom.auth.TokenException;
import com.zipdabackend.global.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SecurityAuthenticationProvider {
    private final JwtProvider jwtProvider;

    public Authentication authentication(String token) {
        Claims claims = jwtProvider.extractClaims(token);
        if(!"ACCESS".equals(claims.get("tokenType", String.class))) {
            throw new TokenException("유효한 토큰 종류가 아닙니다.");
        }
        String role = claims.get("role", String.class);
        if(role == null || role.isBlank()) {
            throw new TokenException("토큰에 권한 정보가 없습니다.");
        }
        return new UsernamePasswordAuthenticationToken(
            claims,                                                       // 1. Principal (사용자 정보) -> Claims 넣어놓음
            null,                                                         // 2. Credentials (인증 수단, jwt는 보통 null, 보통 비밀번호나 토큰)
            List.of(new SimpleGrantedAuthority("ROLE_" + role) )     // 3. Authorities (권한 목록)
        );
    }
}
