package com.flab.goodchoice.coupon.application;

import java.util.UUID;

public interface CouponIssueChecker {

    void duplicateCouponIssueCheck(Long memberId, UUID couponToken);
}
