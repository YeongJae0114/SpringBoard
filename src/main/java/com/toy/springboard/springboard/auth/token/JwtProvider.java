package com.toy.springboard.springboard.auth.token;

import com.toy.springboard.springboard.auth.CustomUserDetails;
import com.toy.springboard.springboard.auth.service.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtProvider {
    private final CustomUserDetailsService userDetailsService;

    @Value("${jwt.secret}")
    private String secretKeyPlain;

    private Key secretKey;

    private static final long ACCESS_TOKEN_EXP = 1000 * 60 * 30; // 30분
    private static final long REFRESH_TOKEN_EXP = 1000L * 60 * 60 * 24 * 7; // 7일

    @PostConstruct
    protected void init() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKeyPlain);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    // 1. AccessToken 생성
    public String createAccessToken(String email, List<String> roles) {
        return createToken(email, roles, ACCESS_TOKEN_EXP);
    }

    // 2. RefreshToken 생성
    public String createRefreshToken(String email) {
        return createToken(email, null, REFRESH_TOKEN_EXP); // 역할 필요 없음
    }

    private String createToken(String email, List<String> roles, long expireTime) {
        Claims claims = Jwts.claims().setSubject(email);
        if (roles != null) claims.put("roles", roles);

        Date now = new Date();
        Date expiry = new Date(now.getTime() + expireTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // 3. Authentication 객체 추출
    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);                         // JWT에서 Claims 추출
        String email = claims.getSubject();                        // "sub" Claim = 사용자 식별자
        List<String> roles = (List<String>) claims.get("roles", List.class);  // "roles" Claim = 역할 목록

        List<SimpleGrantedAuthority> authorities = roles == null
                ? List.of()
                : roles.stream().map(SimpleGrantedAuthority::new).toList();  // 권한 객체로 변환
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(userDetails, token, authorities);
    }

    // 4. 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("JWT 유효성 검사 실패: {}", e.getMessage()); // 🔥 이유 정확히 확인 가능
            return false;
        }
    }

    // 5. HTTP 요청에서 토큰 꺼내기
    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        return (bearer != null && bearer.startsWith("Bearer ")) ? bearer.substring(7) : null;
    }

    // 내부 Claims 파싱용
    private Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }
}
