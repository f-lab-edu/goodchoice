package com.flab.goodchoicecoupon.application;

import com.flab.goodchoicecoupon.domain.CouponIssueFailed;

public interface CouponIssueFailedCommand {
    CouponIssueFailed save(CouponIssueFailed couponIssueFailedEvent);
}
