package com.flab.goodchoiceapi.coupon.application;

import com.flab.goodchoicecoupon.domain.CouponIssue;

import java.util.List;
import java.util.UUID;

public interface CouponIssueQuery {
    List<CouponIssue> getCouponIssues(Long memberId);

    CouponIssue getCouponIssue(UUID couponIssueToken, Long memberId);
}
