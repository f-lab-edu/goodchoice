package com.flab.goodchoice.coupon.dto;

import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.State;

import java.util.UUID;

public record CouponInfoResponse(
        UUID couponToken,
        String couponName,
        int stock,
        State state
) {

    public static CouponInfoResponse of (Coupon coupon) {
        return new CouponInfoResponse(coupon.getCouponToken(), coupon.getCouponName(), coupon.getStock(), coupon.getState());
    }
}
