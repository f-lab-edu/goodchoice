package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.CouponIssue;

public interface CouponIssueCommand {
    CouponIssue save(CouponIssue couponIssue);

    void modify(CouponIssue couponIssue);
}
