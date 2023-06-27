package com.flab.goodchoice.coupon.dto;

import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.State;

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
