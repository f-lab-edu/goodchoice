package com.flab.goodchoice.coupon.application;

import java.util.UUID;

public interface CouponIssueExistCheck {

    boolean duplicateCouponIssue(Long memberId, UUID couponToken);
}
