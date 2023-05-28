package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.CouponUseHistory;
import com.flab.goodchoice.coupon.infrastructure.entity.CouponUseHistoryEntity;

public interface CouponUseHistoryCommand {
    CouponUseHistoryEntity save(CouponUseHistory couponUseHistory);
}
