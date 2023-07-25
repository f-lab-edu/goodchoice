package com.flab.goodchoiceapi.coupon.application;

import com.flab.goodchoicecoupon.domain.Coupon;
import com.flab.goodchoicecoupon.domain.CouponUseHistory;
import com.flab.goodchoicemember.domain.model.Member;

public interface CouponUseHistoryQuery {
    CouponUseHistory getCouponUseHistory(Member member, Coupon coupon);
}
