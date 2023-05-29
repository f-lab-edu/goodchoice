package com.flab.goodchoice.coupon.dto;

import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.CouponType;

import java.util.UUID;

public record MemberSpecificCouponResponse(
        UUID couponId,
        String couponName,
        CouponType couponType,
        int discountValue
) {

    public static MemberSpecificCouponResponse of (Coupon coupon) {
        return new MemberSpecificCouponResponse(coupon.getCouponToken(), coupon.getCouponName(), coupon.getCouponType(), coupon.getDiscountValue());
    }
}
