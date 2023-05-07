package com.flab.goodchoice.coupon.dto;


public record CreateCouponRequest(
        String couponName,
        int stock
) {
}
