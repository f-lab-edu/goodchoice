package com.flab.goodchoicecoupon.application;

import com.flab.goodchoicecoupon.domain.CouponIssueFailedEvent;

public interface CouponIssueFailedEventQuery {
    CouponIssueFailedEvent getCouponIssueFailedEvent(Long couponIssueFailedEventId);
}
