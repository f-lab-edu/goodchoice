package com.flab.goodchoice.coupon.application;

import java.util.UUID;

public interface CouponIssueExistCheck {

    boolean couponIssueCheck(Long memberId, UUID couponToken);
}
