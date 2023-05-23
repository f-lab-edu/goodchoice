package com.flab.goodchoice.coupon.dto;

public record ModifyCouponRequest(
        String couponName,
        int stock
) {
}
