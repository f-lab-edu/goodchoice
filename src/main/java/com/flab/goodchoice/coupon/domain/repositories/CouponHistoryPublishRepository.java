package com.flab.goodchoice.coupon.domain.repositories;

import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.CouponPublishHistory;

public interface CouponHistoryPublishRepository {
    CouponPublishHistory save(CouponPublishHistory couponPublishHistory);

    int countByCoupon(Coupon coupon);
}
