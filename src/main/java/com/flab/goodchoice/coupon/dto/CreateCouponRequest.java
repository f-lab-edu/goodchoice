package com.flab.goodchoice.coupon.dto;


import com.flab.goodchoice.coupon.domain.CouponType;

public record CreateCouponRequest(
        String couponName,
        int stock,
        CouponType couponType,
        int discountValue
) {
}
