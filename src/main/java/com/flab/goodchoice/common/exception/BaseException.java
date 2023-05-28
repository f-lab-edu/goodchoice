package com.flab.goodchoice.common.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaseException extends RuntimeException {

    private String errorCode;
    private String message;

    public BaseException(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
