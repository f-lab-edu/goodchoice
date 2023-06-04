package com.flab.goodchoice.common.aop;

import com.flab.goodchoice.coupon.infrastructure.repositories.RedisLockRepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Order(1)
@Component
@Slf4j
public class RettuceLockAop {

    private final RedisLockRepository redisLockRepository;

    public RettuceLockAop(RedisLockRepository redisLockRepository) {
        this.redisLockRepository = redisLockRepository;
    }

    @Around("@annotation(com.flab.goodchoice.common.aop.RettuceLock)")
    public Object lock(final ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RettuceLock rettuceLock = method.getAnnotation(RettuceLock.class);

        String key = createKey(signature.getParameterNames(), joinPoint.getArgs(), rettuceLock.key());

        try {
            while (!redisLockRepository.lock(key)) {
                Thread.sleep(50);
            }

            return joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            redisLockRepository.unLock(key);
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
