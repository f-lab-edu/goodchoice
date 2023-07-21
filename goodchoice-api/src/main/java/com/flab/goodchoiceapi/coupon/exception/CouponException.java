package com.flab.goodchoiceapi.coupon.exception;

import com.flab.goodchoiceapi.common.exception.BaseException;

public class CouponException extends BaseException {

    public CouponException(String errorCode, String message) {
        super(errorCode, message);
    }

    public CouponException(CouponError couponError) {
        this(couponError.getErrorCode(), couponError.getMessage());
    }
}
