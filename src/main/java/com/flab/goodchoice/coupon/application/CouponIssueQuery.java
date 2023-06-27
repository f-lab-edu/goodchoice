package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.CouponIssue;

import java.util.List;
import java.util.UUID;

public interface CouponIssueQuery {
    List<CouponIssue> getCouponIssue(Long memberId);

    CouponIssue getCouponIssue(UUID couponIssueToken, Long memberId);
}
