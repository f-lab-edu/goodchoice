package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.CouponPublish;

import java.util.List;
import java.util.UUID;

public interface CouponPublishQuery {
    List<CouponPublish> getCouponIssue(Long memberId);

    CouponPublish getCouponPublishInfo(UUID couponPublishToken, Long memberId);
}
