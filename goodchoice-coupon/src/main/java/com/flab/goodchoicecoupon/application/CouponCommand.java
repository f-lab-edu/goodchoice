package com.flab.goodchoicecoupon.application;

import com.flab.goodchoicecoupon.domain.Coupon;

public interface CouponCommand {
    Coupon save(Coupon coupon);

    void modify(Coupon coupon);
}
