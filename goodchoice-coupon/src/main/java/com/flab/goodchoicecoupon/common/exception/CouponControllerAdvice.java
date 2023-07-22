package com.flab.goodchoicecoupon.common.exception;

import com.flab.goodchoicecoupon.common.response.CouponCommonResponse;
import com.flab.goodchoicecoupon.exception.CouponException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CouponControllerAdvice {

    @ExceptionHandler(CouponException.class)
    public CouponCommonResponse<Void> onException(CouponException couponException) {
        return CouponCommonResponse.fail(couponException.getErrorCode(), couponException.getMessage());
    }
}
