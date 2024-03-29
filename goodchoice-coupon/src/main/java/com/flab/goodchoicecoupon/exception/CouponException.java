package com.flab.goodchoicecoupon.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CouponException extends RuntimeException {

    private String errorCode;
    private String message;

    public CouponException(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public CouponException(CouponError couponError) {
        this(couponError.getErrorCode(), couponError.getMessage());
    }
}
