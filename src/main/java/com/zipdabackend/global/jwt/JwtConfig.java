package com.zipdabackend.global.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.jwt")
public record JwtConfig(
        boolean secure,
        String issuer,
        String type,
        int accessTokenExpiry,
        int adminAccessTokenExpiry,
        int refreshTokenExpiry,
        String refreshTokenCookieName,
        int refreshTokenCookieExpiry,
        String secret,
        String authorizationHeaderName,
        String scheme,
        String refreshTokenCookiePath
) {}
