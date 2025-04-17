package com.toy.springboard.springboard.auth.token;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {
    private final RedisTemplate<String, String> redisTemplate;
    private static final String PREFIX = "springboard:auth:refresh:";

    // 토큰 저장
    public void save(String userId, String refreshToken, long expirationMillis) {
        String key = PREFIX + userId;
        redisTemplate.opsForValue().set(key, refreshToken, Duration.ofMillis(expirationMillis));
    }

    // 토큰 조회
    public String findByUserId(String userId) {
        return redisTemplate.opsForValue().get(PREFIX + userId);
    }

    // 토큰 삭제
    public void deleteByUserId(String userId) {
        redisTemplate.delete(PREFIX + userId);
    }
}
