package com.flab.goodchoice.common.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LimitedCountLock {

    String key();

    long waitTime() default 10L;

    long leaseTime() default 1L;

    TimeUnit timeUnit() default TimeUnit.SECONDS;
}
