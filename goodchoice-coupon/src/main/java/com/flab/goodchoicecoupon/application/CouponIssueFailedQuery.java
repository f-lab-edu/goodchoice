package com.flab.goodchoicecoupon.application;

import com.flab.goodchoicecoupon.domain.CouponIssueFailed;

public interface CouponIssueFailedQuery {
    CouponIssueFailed getCouponIssueFailedEvent(Long couponIssueFailedEventId);
}
