package com.flab.goodchoiceredis.infrastructure.support;

import com.flab.goodchoiceredis.exception.RedisError;
import com.flab.goodchoiceredis.exception.RedisException;
import com.flab.goodchoiceredis.infrastructure.AppliedUserRepository;
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
        Long apply = redisTemplate
                .opsForSet()
                .add(key, memberId);

        if (apply != 1) {
            throw new RedisException(RedisError.NOT_DUPLICATION_COUPON);
        }

        return apply;
    }
}
