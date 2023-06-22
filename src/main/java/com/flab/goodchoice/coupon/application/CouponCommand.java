package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.Coupon;

public interface CouponCommand {
    Coupon save(Coupon coupon);

    void modify(Coupon coupon);
}
