package com.flab.goodchoiceapi.coupon.application;

import com.flab.goodchoicecoupon.domain.Coupon;

public interface CouponCommand {
    Coupon save(Coupon coupon);

    void modify(Coupon coupon);
}
