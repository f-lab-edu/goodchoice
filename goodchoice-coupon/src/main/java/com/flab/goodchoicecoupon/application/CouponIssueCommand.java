package com.flab.goodchoicecoupon.application;

import com.flab.goodchoicecoupon.domain.CouponIssue;

public interface CouponIssueCommand {
    CouponIssue save(CouponIssue couponIssue);

    void modify(CouponIssue couponIssue);
}
