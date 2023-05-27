package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.CouponPublish;

public interface CouponPublishCommand {
    CouponPublish save(CouponPublish couponPublish);

    void modify(CouponPublish couponPublish);
}
