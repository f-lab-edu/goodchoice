package com.flab.goodchoicecoupon.infrastructure;

import com.flab.goodchoicecoupon.application.CouponIssueCommand;
import com.flab.goodchoicecoupon.domain.CouponIssue;
import com.flab.goodchoicecoupon.exception.CouponException;

public class FakeErrorCouponIssueCommand implements CouponIssueCommand {
    @Override
    public CouponIssue save(CouponIssue couponIssue) {
        throw new CouponException();
    }

    @Override
    public void modify(CouponIssue couponIssue) {
        throw new CouponException();
    }
}
