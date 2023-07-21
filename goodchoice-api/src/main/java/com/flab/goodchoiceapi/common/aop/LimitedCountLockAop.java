package com.flab.goodchoiceapi.common.aop;

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
public class LimitedCountLockAop {

    private final RedissonClient redissonClient;

    public LimitedCountLockAop(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Around("@annotation(com.flab.goodchoiceapi.common.aop.LimitedCountLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LimitedCountLock limitedCountLock = method.getAnnotation(LimitedCountLock.class);

        String key = createKey(signature.getParameterNames(), joinPoint.getArgs(), limitedCountLock.key());

        RLock lock = redissonClient.getLock(key);
        try {
            boolean available = lock.tryLock(limitedCountLock.waitTime(), limitedCountLock.leaseTime(), limitedCountLock.timeUnit());

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

    private String createKey(String[] parameterNames, Object[] args, String key) {
        String resultKey = key;

        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterNames[i].equals(key)) {
                resultKey += args[i];
                break;
            }
        }

        return resultKey;
    }
}
