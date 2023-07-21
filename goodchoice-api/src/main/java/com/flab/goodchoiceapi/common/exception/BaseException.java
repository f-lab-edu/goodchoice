package com.flab.goodchoiceapi.common.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseException extends RuntimeException {

    protected String errorCode;
    protected String message;

    public BaseException(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
