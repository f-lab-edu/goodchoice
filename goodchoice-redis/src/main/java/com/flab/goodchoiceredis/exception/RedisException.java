package com.flab.goodchoiceredis.exception;

import lombok.Getter;

@Getter
public class RedisException extends RuntimeException {

    private String errorCode;
    private String message;

    public RedisException(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public RedisException(RedisError couponError) {
        this(couponError.getErrorCode(), couponError.getMessage());
    }
}
