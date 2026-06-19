package com.zipdabackend.global.security;

import com.zipdabackend.global.config.CorsConfig;
import com.zipdabackend.global.constant.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final CorsConfig corsConfig;

    @Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

    // cross-orgin-resource-sharing일때 무슨 메서드와 헤더를 허용할것인지 지정
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // CorsConfiguration 객체 생성: CORS 규칙을 담는 Spring 객체
        CorsConfiguration configuration = new CorsConfiguration();

        // 허용할 프론트엔드 도메인 설정
        configuration.setAllowedOrigins(corsConfig.allowedOrigins());

        // 허용할 HTTP Method 지정
        configuration.setAllowedMethods(List.of(
                HttpMethod.GET.name()
                , HttpMethod.POST.name()
                , HttpMethod.PUT.name()
                , HttpMethod.PATCH.name()
                , HttpMethod.DELETE.name()
                , HttpMethod.OPTIONS.name() // preflight 요청 허용 -> 이해 제대로 안됨
        ));

        // 허용할 헤더 지정
        configuration.setAllowedHeaders(List.of(
                HttpHeaders.AUTHORIZATION // JWT 담는 헤더
                ,HttpHeaders.CONTENT_TYPE // JSON인지 mutipart인지 알려주는 헤더
                ,HttpHeaders.ACCEPT // 어떤 응답 타입을 받을지 알려주는 헤더
        ));

        // CORS 상황에서도 자격증명(Cookie, 인증 헤더 정보 등등) 포함 여부 설정
        configuration.setAllowCredentials(true);

        // 브라우저가 preflight 요청 결과를 캐시할 시간(초 단위) 설정
        configuration.setMaxAge(corsConfig.maxAge());

        // 모든 API 경로에 위 설정을 적용
        // UrlBasedCorsConfigurationSource 객체 : 어떤 URL 경로에 어떤 CORS 설정 적용할지 관리
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 백엔드 서버의 모든 경로에 CORS 설정 적용
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    // SecurityUrlRegistry를 참조하여 권한 검사, 이 유저가 이 URL get or patch에 접근이 가능한가?
    // if success -> 우리가 만든 @controller
    // else if fail -> SecurityExceptionHandler (commence or handle) -> HandlerExceptionResolver
    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http, // Spring security 설정을 체인 방식으로 작성하게 해주는 객체
            SecurityExceptionHandler securityExceptionHandler,
            TokenAuthenticationFilter tokenAuthenticationFilter
    ) throws Exception {
        return http
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 설정 비활성
                .httpBasic(AbstractHttpConfigurer::disable) // 브라우저 기본 인증창 비활성화.
                .formLogin(AbstractHttpConfigurer::disable) //Spring Security 기본 로그인 페이지 비활성화.
                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화.
                // 아까 만든 CORS 설정을 Security Filter Chain에 적용
                .cors(cors -> cors.configurationSource(this.corsConfigurationSource())) // Cors 설정 추가
                // TokenAuthenticationFilter를 Spring Security 필터 체인에 등록함
                // UsernamePasswordAuthenticationFilter보다 먼저 TokenAuthenticationFilter를 실행해라.
                .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // 필터 등록
                .authorizeHttpRequests(req ->
                        // 요청에 대한 권한 설정
                        req.requestMatchers(HttpMethod.GET, SecurityUrlRegistry.USER_AGENT_GET_URLS).hasAnyRole(UserRole.USER.name(), UserRole.AGENT.name())
                                .requestMatchers(HttpMethod.DELETE, SecurityUrlRegistry.USER_AGENT_DELETE_URLS).hasAnyRole(UserRole.USER.name(), UserRole.AGENT.name())
                                .requestMatchers(HttpMethod.PATCH, SecurityUrlRegistry.USER_AGENT_PATCH_URLS).hasAnyRole(UserRole.USER.name(), UserRole.AGENT.name())
                                .requestMatchers(HttpMethod.POST, SecurityUrlRegistry.USER_AGENT_POST_URLS).hasAnyRole(UserRole.USER.name(), UserRole.AGENT.name())
                                .requestMatchers(HttpMethod.POST, SecurityUrlRegistry.USER_POST_URLS).hasRole(UserRole.USER.name())
                                .requestMatchers(HttpMethod.GET, SecurityUrlRegistry.USER_GET_URLS).hasRole(UserRole.USER.name())
                                .requestMatchers(HttpMethod.DELETE, SecurityUrlRegistry.ADMIN_DELETE_URLS).hasRole(UserRole.ADMIN.name())
                                .requestMatchers(HttpMethod.POST, SecurityUrlRegistry.AGENT_POST_URLS).hasRole(UserRole.AGENT.name())
                                .requestMatchers(HttpMethod.GET, SecurityUrlRegistry.ADMIN_GET_URLS).hasRole(UserRole.ADMIN.name())
                                .requestMatchers(HttpMethod.PATCH, SecurityUrlRegistry.ADMIN_PATCH_URLS).hasRole(UserRole.ADMIN.name())
                                .anyRequest().permitAll() // 그 외는 인증 불필요
                )
                // 예외가 발생했을 때 어떻게 응답?
                .exceptionHandling(e ->
                        e
                        // 인증이 안 된 사용자가 인증이 필요한 API에 접근할 때 실행됨
                        .authenticationEntryPoint(securityExceptionHandler)
                        // 로그인은 했지만 권한이 부족할 때 실행됨
                        .accessDeniedHandler(securityExceptionHandler)
                )
                .build();
    }
}
