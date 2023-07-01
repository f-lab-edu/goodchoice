package com.flab.goodchoice.coupon.infrastructure;

import com.flab.goodchoice.coupon.infrastructure.repositories.AppliedUserRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AppliedUserRepositoryImpl implements AppliedUserRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public AppliedUserRepositoryImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Long addRedisSet(String key, String memberId) {
        return redisTemplate
                .opsForSet()
                .add(key, memberId);
    }
}
