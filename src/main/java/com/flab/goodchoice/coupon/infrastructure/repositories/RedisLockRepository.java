package com.flab.goodchoice.coupon.infrastructure.repositories;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisLockRepository {

    private RedisTemplate<String, String> redisTemplate;

    public RedisLockRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Boolean lock(final String key) {
        return redisTemplate
                .opsForValue()
                .setIfAbsent(key, "lock", Duration.ofMillis(3_000));
    }

    public Boolean unLock(final String key) {
        return redisTemplate.delete(key);
    }
}
