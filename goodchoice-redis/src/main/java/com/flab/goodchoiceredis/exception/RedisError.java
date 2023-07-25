package com.flab.goodchoiceredis.exception;

import lombok.Getter;

@Getter
public enum RedisError {
    NOT_DUPLICATION_COUPON("C003", "한계정당 하나의 쿠폰을 가질 수 있습니다.");

    private final String errorCode;
    private final String message;

    RedisError(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
