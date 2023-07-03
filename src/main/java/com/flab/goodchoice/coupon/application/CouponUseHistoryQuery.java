package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.CouponUseHistory;
import com.flab.goodchoice.member.domain.model.Member;

public interface CouponUseHistoryQuery {
    CouponUseHistory getCouponUseHistory(Member member, Coupon coupon);
}
