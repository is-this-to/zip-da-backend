package com.zipdabackend.global.jwt;

import com.zipdabackend.domain.user.entity.User;
import com.zipdabackend.global.cookie.CookieManager;
import com.zipdabackend.global.error.custom.TokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtProvider {
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;
    private final CookieManager cookieManager;

    public JwtProvider(JwtConfig jwtConfig, CookieManager cookieManager) {
        this.jwtConfig = jwtConfig;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfig.secret()));
        this.cookieManager = cookieManager;
    }

    private String generateToken(User user, long ttl) {
        Date now = new Date();
        return Jwts.builder()
                .header() // jwt 헤더 설정 JwtBuilder -> BuilderHeader 객체로
                .type(jwtConfig.type())
                .and() // 헤더 설정 끝 -> 다움부터 payload 설정 ->  BuilderHeader 객체에서 JwtBuilder 객체로
                .subject(String.valueOf(user.getUserId()))
                .issuer(jwtConfig.issuer())
                .expiration(new Date(now.getTime() + ttl)) // 밀리초
                .claim("role", user.getRole())
                .signWith(secretKey) // secretKey로 jwt 서명
                .compact(); // JWT를 최종 문자열로 만든다 -> Header.Payload.Signature 형태로 만듦
    }

    public String generateAccessToken(User user) {
        return this.generateToken(user, jwtConfig.accessTokenExpiry());
    }
    public String generateRefreshToken(User user) {
        return this.generateToken(user, jwtConfig.refreshTokenExpiry());
    }

    // request에서 cookie에 있는 refreshToken 추출
    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return cookieManager.getCookie(request, jwtConfig.refreshTokenCookieName())
                .map(Cookie::getValue);
    }

    // request http 헤더의 Authorization에 저장된 accessToken 추출
    public Optional<String> extractAccessToken(HttpServletRequest request) {
        // request에서 Authorization 토큰을 가져옴
        String authorizationHeader = request.getHeader(jwtConfig.authorizationHeaderName());
        // "Bearer " 문자열을 저장
        String bearerPrefix = jwtConfig.scheme() + " ";

        if(authorizationHeader == null || !authorizationHeader.startsWith(bearerPrefix)) {
            return Optional.empty();
        }

        String accessToken = authorizationHeader.substring(bearerPrefix.length()).trim();

        if(accessToken.isBlank()) {
            return Optional.empty();
        }

        return Optional.of(accessToken);
    }

    // 토큰 검증 + Claims 추출
    public Claims extractClaims(String token) {
        try {
            return Jwts.parser()
                    // 토큰 signature를 검증할때 쓸 SecretKey를 지정하는 부분
                    .verifyWith(this.secretKey)
                    // 앞에서 설정한 조건으로 JWT Parser 완성
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }  catch (ExpiredJwtException e) {
            throw new TokenException("토큰이 만료됐습니다.");
        } catch (UnsupportedJwtException e) {
            throw new TokenException("지원하지 않는 토큰입니다.");
        } catch (MalformedJwtException e) {
            throw new TokenException("토큰형식이 올바르지 않습니다.");
        } catch (SecurityException e) {
            throw new TokenException("토큰 서명 검증에 실패했습니다.");
        } catch (JwtException | IllegalArgumentException e) {
            throw new TokenException("토큰 검증에 실패했습니다.");
        }
    }
}
