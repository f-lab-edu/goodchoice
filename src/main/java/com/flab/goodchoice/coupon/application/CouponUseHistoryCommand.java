package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.CouponUseHistory;

public interface CouponUseHistoryCommand {
    CouponUseHistory save(CouponUseHistory couponUseHistory);

    void modify(CouponUseHistory couponUseHistory);
}
