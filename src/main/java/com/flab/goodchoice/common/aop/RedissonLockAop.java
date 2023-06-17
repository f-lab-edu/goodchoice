package com.flab.goodchoice.common.aop;

import com.flab.goodchoice.coupon.exception.CouponError;
import com.flab.goodchoice.coupon.exception.CouponException;
import com.flab.goodchoice.coupon.infrastructure.repositories.AppliedUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Order(1)
@Component
@Slf4j
public class RedissonLockAop {

    private final RedissonClient redissonClient;
    private final AppliedUserRepository appliedUserRepository;

    public RedissonLockAop(RedissonClient redissonClient, AppliedUserRepository appliedUserRepository) {
        this.redissonClient = redissonClient;
        this.appliedUserRepository = appliedUserRepository;
    }

    @Around("@annotation(com.flab.goodchoice.common.aop.RedissonLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RedissonLock redissonLock = method.getAnnotation(RedissonLock.class);

        String key = createParameter(signature.getParameterNames(), joinPoint.getArgs(), redissonLock.key());
        String target = createParameter(signature.getParameterNames(), joinPoint.getArgs(), redissonLock.target());

        Long apply = appliedUserRepository.addRedisSet(key, target);

        if (apply != 1) {
            throw new CouponException(CouponError.NOT_DUPLICATION_COUPON);
        }

        RLock lock = redissonClient.getLock("key" + key);
        try {
            boolean available = lock.tryLock(redissonLock.waitTime(), redissonLock.leaseTime(), redissonLock.timeUnit());

            if (!available) {
                return false;
            }

            return joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    private String createParameter(String[] parameterNames, Object[] args, String param) {
        String result = param;

        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterNames[i].equals(param)) {
                result = String.valueOf(args[i]);
                break;
            }
        }

        return result;
    }
}
