package com.toy.springboard.springboard.config;

import com.toy.springboard.springboard.auth.token.JwtAuthenticationFilter;
import com.toy.springboard.springboard.auth.token.JwtProvider;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtProvider jwtProvider;

    private static final String[] PUBLIC_STATIC_RESOURCES = {
            "/", "/css/**", "/images/**", "/js/**", "/favicon.ico", "/h2-console/**" , "/actuator/prometheus/**"
    };
    private static final String[] PUBLIC_API_ENDPOINTS = {
            "/api/auth/**", "/api/public/**",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .securityContext(securityContext->securityContext.requireExplicitSave(false))
                .cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedMethods(List.of("GET", "POST", "PATCH", "DELETE", "OPTIONS")); // 필요한 메서드만 허용
                    configuration.setAllowCredentials(true); // 세션 기반 인증
                    configuration.setMaxAge(7200L); // Preflight 요청 캐싱
                    configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept")); // 명시적으로 허용할 헤더
                    configuration.setExposedHeaders(List.of("Access", "Authorization")); // 노출할 헤더 추가
                    return configuration;
                }))
                .csrf(AbstractHttpConfigurer::disable) // CSRF 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // 로그인 폼 활성화
                .httpBasic(AbstractHttpConfigurer::disable) // HTTP Basic 인증 비활성화
                // 경로별 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_STATIC_RESOURCES).permitAll()
                        .requestMatchers(PUBLIC_API_ENDPOINTS).permitAll()
                        .anyRequest().authenticated() // 그 외 요청은 인증 필요
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
