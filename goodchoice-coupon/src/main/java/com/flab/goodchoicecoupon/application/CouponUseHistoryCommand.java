package com.flab.goodchoicecoupon.application;

import com.flab.goodchoicecoupon.domain.CouponUseHistory;

public interface CouponUseHistoryCommand {
    CouponUseHistory save(CouponUseHistory couponUseHistory);

    void modify(CouponUseHistory couponUseHistory);
}
