package com.flab.goodchoicecoupon.application;

import com.flab.goodchoicecoupon.domain.CouponIssueFailedEvent;

public interface CouponIssueFailedEventCommand {
    CouponIssueFailedEvent save(CouponIssueFailedEvent couponIssueFailedEvent);
}
