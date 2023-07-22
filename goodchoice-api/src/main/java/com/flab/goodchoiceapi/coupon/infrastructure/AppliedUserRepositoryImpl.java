package com.flab.goodchoiceapi.coupon.infrastructure;

import com.flab.goodchoicecoupon.exception.CouponError;
import com.flab.goodchoicecoupon.exception.CouponException;
import com.flab.goodchoicecoupon.infrastructure.repositories.AppliedUserRepository;
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
            throw new CouponException(CouponError.NOT_DUPLICATION_COUPON);
        }

        return apply;
    }
}
