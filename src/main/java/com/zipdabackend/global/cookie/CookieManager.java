package com.zipdabackend.global.cookie;

import com.zipdabackend.global.jwt.JwtConfig;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CookieManager {

    private final JwtConfig jwtConfig;

    // request에서 특정 이름 쿠키 획득하기
    public Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        if(request.getCookies() == null) {
            return Optional.empty();
        }
        // header에 있는 쿠키들 전부 배열로 return해줌 -> 특정 이름의 cookie만 필터 -> 그 중 첫번째 쿠키만 빼옴
        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(name))
                .findFirst();
    }

    // response 헤더에 만든 쿠키 넣어주기
    public void setCookie(HttpServletResponse response, String name, String value, int maxAge, String path) {
        Cookie cookie = new Cookie(name, value);
        // 브라우저가 path url로 요청할 때만 이 쿠키를 서버로 보냄
        cookie.setPath(path);
        // 쿠키 유효 수명 지정 -> 지정된 시간(초)가 지나면 브라우저가 쿠키를 폐기
        cookie.setMaxAge(maxAge);
        // 자바스크립을 통해 쿠키 접근 막기 -> httpOnly 설정
        cookie.setHttpOnly(true);
        // 브라우저가 HTTPS Request를 보낼때만 쿠키 보낼거야? -> 개발 과정에서는 http로 보내기 때문에 false
        cookie.setSecure(jwtConfig.secure());
        response.addCookie(cookie);
    }
}
