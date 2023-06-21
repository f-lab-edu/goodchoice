package com.flab.goodchoice.coupon.application;

import java.util.UUID;

public interface CouponPublishExistCheck {

    boolean couponIssueCheck(Long memberId, UUID couponToken);
}
