package com.flab.goodchoiceapi.coupon.dto;

import com.flab.goodchoicecoupon.domain.Coupon;
import com.flab.goodchoicecoupon.domain.State;

import java.util.UUID;

public record CouponRetrievalResponse(
        UUID couponToken,
        String couponName,
        int stock,
        State state
) {

    public static CouponRetrievalResponse of (Coupon coupon) {
        return new CouponRetrievalResponse(coupon.getCouponToken(), coupon.getCouponName(), coupon.getStock(), coupon.getState());
    }
}
