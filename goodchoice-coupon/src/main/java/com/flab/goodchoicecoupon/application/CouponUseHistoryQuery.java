package com.flab.goodchoicecoupon.application;

import com.flab.goodchoicecoupon.domain.Coupon;
import com.flab.goodchoicecoupon.domain.CouponUseHistory;

public interface CouponUseHistoryQuery {
    CouponUseHistory getCouponUseHistory(Long memberId, Coupon coupon);
}
